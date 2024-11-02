package com.example.bumpbeats.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.bumpbeats.ui.theme.BumpBeatsTheme

@Composable
fun SignUpScreen(
    onSignUpSubmit: (String, String, Int, String, String) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age.value,
            onValueChange = { age.value = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val ageInt = age.value.toIntOrNull() ?: 0
                onSignUpSubmit(name.value, lastName.value, ageInt, email.value, password.value)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    BumpBeatsTheme {
        SignUpScreen { _, _, _, _, _ -> }
    }
}

