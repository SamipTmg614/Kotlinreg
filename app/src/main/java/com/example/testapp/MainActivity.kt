package com.example.testapp

import android.graphics.BitmapFactory
import android.graphics.ColorSpace.Rgb
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.ui.theme.TestAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalInspectionMode
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    mainScreen(innerPadding)
                }
            }
        }
    }
}


@Composable
fun mainScreen(innerPadding: PaddingValues){
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context,R.raw.song) }
    var isPLaying by remember { mutableStateOf(false) }

    val albumArt = loadAlbumArt()
    val musicName = getMusicTitle()

    var progress by remember { mutableFloatStateOf(0f) }
    val duration = mediaPlayer.duration
    var currentPosition by remember { mutableIntStateOf(0) }
    LaunchedEffect(isPLaying) {
        while (isPLaying) {
            currentPosition = mediaPlayer.currentPosition
            progress = mediaPlayer.currentPosition / duration.toFloat()
            delay(500L) //HALF SECOND DELAY
        }
    }

    Column (modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color(red = 65, green = 65,blue = 65)),
        ){
        Row (modifier = Modifier.fillMaxWidth().height(225.dp)){  }
        Row (modifier = Modifier.fillMaxWidth().height(200.dp),
            horizontalArrangement = Arrangement.Center){
            if (albumArt != null){
                println("there is art")
                Image(
                    bitmap = albumArt,
                    contentDescription = "Album Art",
                    modifier = Modifier.size(200.dp)
                )
            }else{
                Box(modifier = Modifier.size(200.dp).background(Color.White))

            }

        }
        Row (modifier = Modifier.fillMaxWidth().height(60.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){

            Text(text = musicName,
                fontSize = 25.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(250.dp),
                )

        }
        Row (horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)){
            Text(text = formatTime(duration),
                fontSize = 4.sp)
            Slider(
                value = progress,
                onValueChange = {newValue -> progress = newValue},
                onValueChangeFinished = {
                    mediaPlayer.seekTo((progress*duration).toInt())
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).height(4.dp).clip(
                    RoundedCornerShape(50)) ,
                colors = SliderDefaults.colors(
                    thumbColor= Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Black

                )

            )
        }
        Row (modifier = Modifier.fillMaxWidth().height(150.dp).padding(top = 50.dp).background(Color.Gray),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            ){
            Icon(
                painter = painterResource(R.drawable.baseline_skip_previous_24), contentDescription = "previous",
                modifier = Modifier.height(50.dp).clickable{
                    val newPos = (mediaPlayer.currentPosition - 10_000).coerceAtLeast(0)
                    mediaPlayer.seekTo(newPos)
                }
            )
            Icon(
                painter = painterResource(
                    if (isPLaying) R.drawable.pause else R.drawable.baseline_play_arrow_24
                ), contentDescription = "Play/Pause",
                modifier = Modifier.height(50.dp).clickable {
                    if (isPLaying) {
                        mediaPlayer.pause()
                    } else {
                        mediaPlayer.start()
                    }
                    isPLaying =  !isPLaying
                }
            )
            Icon(
                painter = painterResource(R.drawable.baseline_skip_next_24), contentDescription = "next",
                modifier = Modifier.height(50.dp).clickable{
                    val newPos = (mediaPlayer.currentPosition + 10_000).coerceAtLeast(0)
                    mediaPlayer.seekTo(newPos)
                }
            )


//            Icon(
//                painter = painterResource(R.drawable.baseline_library_music_24), contentDescription = null,
//                modifier = Modifier.height(50.dp)
//            )

        }


    }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }


}

private fun RowScope.formatTime(ms: Int): String {
    val totalSec = ms / 1000
    val min = totalSec / 60
    val sec = totalSec % 60
    return "%d:%02d".format(min, sec)
}


@Composable
fun loadAlbumArt(): ImageBitmap? {
    val isInPreview = LocalInspectionMode.current
    if (isInPreview) {return null}

    val context = LocalContext.current
    val retriever = MediaMetadataRetriever()
    val fd = context.resources.openRawResourceFd(R.raw.song)
    retriever.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)

    val art = retriever.embeddedPicture
    fd.close()
    retriever.release()

    return art?.let {
        BitmapFactory.decodeByteArray(it, 0, it.size)?.asImageBitmap()
    }
}

@Composable
fun getMusicTitle(): String {
    val isPreview = LocalInspectionMode.current
    if (isPreview) return "Song Title"

    val context = LocalContext.current

    val title = remember {
        try {
            val retriever = MediaMetadataRetriever()
            val fd = context.resources.openRawResourceFd(R.raw.song)
            retriever.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
            val titleMeta = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            fd.close()
            retriever.release()
            titleMeta ?: "Unknown Title"
        } catch (e: Exception) {
            "Unknown Title"
        }
    }
    (return title).toString()
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    mainScreen(innerPadding = PaddingValues(0.dp))
}
