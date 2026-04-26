package com.example.capocoinapp


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capocoinapp.ui.theme.Accent
import com.example.capocoinapp.ui.theme.BackgroundColor
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.Primary
import com.example.capocoinapp.ui.theme.SubTextWhite
import com.example.capocoinapp.ui.theme.TextWhite


@Composable
fun Register(
    modifier: Modifier = Modifier,
    message: String = "",
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
            .background(color = BackgroundColor)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Create account",
            style= MaterialTheme.typography.displayMedium,
            color = Accent,
            fontWeight = FontWeight.Bold
            )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text= "Name",
            color=TextWhite,
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
                Text("Enter your name.", color = SubTextWhite)
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text= "Email",
            color=TextWhite,
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
                Text("Enter your email.", color = SubTextWhite)
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text= "Username",
            color=TextWhite,
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
                Text("Enter your username.", color = SubTextWhite)
            }
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text= "Password",
            color=TextWhite,
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
                Text("Enter your password.", color = SubTextWhite)
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        var passwordError by rememberSaveable { mutableStateOf(false) }

        Text(
            text= "Confirm Password",
            color=TextWhite,
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
                Text("Enter your password.", color = SubTextWhite)
            }
        )

        if (message.isNotBlank()) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }
        FilledTonalButton(onClick = {
            onRegisterClick(name,
                username,
                email,
                password,
                confirmPassword)
        },
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = Primary,
                contentColor = Accent
            )
        ){
            Text("Register")
        }

    }
}


@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    CapoCoinAppTheme {
        Register()
    }
}

