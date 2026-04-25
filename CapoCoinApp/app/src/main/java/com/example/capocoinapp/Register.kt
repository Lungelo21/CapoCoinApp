package com.example.capocoinapp


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun Register(
    modifier: Modifier = Modifier,
    onRegisterClick: (name:String,
                      username:String,
                      email:String,
                      password:String,
                      confirmPassword:String) -> Unit =  { _, _, _, _, _ -> }
){
    var name by rememberSaveable { mutableStateOf("") }
    var email by  rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable() {mutableStateOf("") }
    var confirmPassword by rememberSaveable() { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Create account",
            style= MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
            )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text= "Name",
            style= MaterialTheme.typography.titleLarge,
            modifier= Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Enter your name.")
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text= "Email",
            style= MaterialTheme.typography.titleLarge,
            modifier= Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Enter your email.")
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text= "Username",
            style= MaterialTheme.typography.titleLarge,
            modifier= Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = username,
            onValueChange = {
                username = it
            },
            label = {
                Text("Enter your username.")
            }
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text= "Password",
            style= MaterialTheme.typography.titleLarge,
            modifier= Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Enter your password.")
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        var passwordError by rememberSaveable { mutableStateOf(false) }

        Text(
            text= "Confirm Password",
            style= MaterialTheme.typography.titleLarge,
            modifier= Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            value = confirmPassword,
            isError = passwordError,
            supportingText = {
                if(passwordError){
                    Text("Passwords don't Match")
                }
            },
            onValueChange = {
                confirmPassword = it
                passwordError = it != password
            },
            label = {
                Text("Enter your password.")
            }
        )

        FilledTonalButton(onClick = {
            onRegisterClick(name,
                username,
                email,
                password,
                confirmPassword)
        }){Text("Register")}

    }
}

