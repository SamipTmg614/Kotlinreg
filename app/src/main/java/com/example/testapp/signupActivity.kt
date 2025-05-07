package com.example.testapp

import android.os.Bundle
import android.text.Layout
import android.widget.Button
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.testapp.ui.theme.TestAppTheme
import org.intellij.lang.annotations.JdkConstants
import java.util.Calendar
import android.app.DatePickerDialog
import androidx.compose.material3.RadioButton
import androidx.compose.ui.Alignment

class signupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        MainBody(innerPadding)

                }
            }
        }
    }
}

@Composable
fun MainBody(innerPadding: PaddingValues){
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var terms by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedoptionText by remember { mutableStateOf("Select option") }
    var TextFieldSize by remember {mutableStateOf(Size.Zero)}
    var options =listOf("Nepal","india","China","other")


    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var dob by remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            dob = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    var selectedGender by remember { mutableStateOf("Male") }
    val genderOptions = listOf("Male", "Female", "Other")

    Column (modifier = Modifier.fillMaxSize().padding(innerPadding),horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = "Register",
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier= Modifier.height(10.dp))
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                modifier = Modifier.width(180.dp),
                placeholder = {Text("first name")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                prefix = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
            )
            Spacer(modifier = Modifier.width(12.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                modifier = Modifier.width(180.dp),
                placeholder = {Text("last name")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                prefix = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.width(372.dp),
            placeholder = {Text("email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            prefix = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
        )
        Box {
            OutlinedTextField(
                value = selectedoptionText,
                onValueChange = {
                    selectedoptionText = it
                },
                modifier = Modifier.width(372.dp).onGloballyPositioned{coordinates ->
                    TextFieldSize = coordinates.size.toSize()
                }.clickable{expanded = true},
                placeholder = {
                    Text(text = "----Select option----")
                },
                enabled = false,
                trailingIcon = {
                    Image(
                        painter = painterResource(if (!expanded){R.drawable.baseline_arrow_drop_down_24} else {R.drawable.baseline_arrow_drop_up_24}),
                        contentDescription = null
                    )
                },
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false},
                modifier = Modifier.width(372.dp)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {Text(option)},
                        onClick = {
                            selectedoptionText = option
                            expanded = true
                        }
                    )
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "DOB", color = Color.Black)


            OutlinedTextField(
                value = dob,
                onValueChange = {},
                modifier = Modifier
                    .width(150.dp)
                    .clickable {
                        datePickerDialog.show()
                    },
                placeholder = { Text("dd/mm/year", color = Color.Black)},
                readOnly = true,
                enabled = false,
            )

        }

        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            genderOptions.forEach { gender ->
                    RadioButton(
                        selected = selectedGender == gender,
                        onClick = { selectedGender = gender }
                    )
                    Text(
                        text = gender,
                        modifier = Modifier.padding(start = 8.dp)
                    )

            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    TestAppTheme {
        MainBody(PaddingValues(0.dp))
    }
}