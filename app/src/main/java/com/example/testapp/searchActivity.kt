package com.example.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapp.ui.theme.TestAppTheme

class searchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAppTheme {
                Scaffold { innerPadding ->
                    searchbody(innerPadding)
                }
            }
        }
    }
}

@Composable
fun searchbody(innerPadding: PaddingValues){

    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(30.dp).width(100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                prefix = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = null
                    )
                }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun previewBody(){
    body(innerPadding = PaddingValues(0.dp))
}