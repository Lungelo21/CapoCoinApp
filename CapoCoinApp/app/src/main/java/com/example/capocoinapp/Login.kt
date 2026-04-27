package com.example.capocoinapp


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capocoinapp.ui.theme.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme


@Composable
fun Login(
    modifier: Modifier = Modifier,

    onLoginClick: (email: String, password: String) -> Unit = { _, _ -> },
    onRegisterClick: () -> Unit = {}

) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val capoColorTextField = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = TextWhite,
        unfocusedBorderColor = SubTextWhite,
        focusedTextColor = TextWhite,
        unfocusedTextColor = TextWhite,
        cursorColor = Accent,
        focusedLabelColor = Accent,
        unfocusedLabelColor = SubTextWhite
    )

    /*
   * Author: Donn Felker
                * Link: https://www.youtube.com/watch?v=VE7mCMK5djM
                * Date Accessed: 27/04/2026
                */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Sign In", fontSize = 24.sp,
            color = Accent,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Takes user email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text("Email address:", color = SubTextWhite)
            },
            colors = capoColorTextField
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Take user password
        OutlinedTextField(
            value = password,

            onValueChange = { password = it },
            label = {
                Text("Password:", color = SubTextWhite)
            },
            colors = capoColorTextField,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // button to login
        FilledTonalButton(
            onClick = {
                onLoginClick(email, password)
            },
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = Primary,
                contentColor = Accent
            )
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Clickable if user forgot password
        Text(
            text = "Forgot Password?",
            color = Accent,
            modifier = Modifier.clickable {

            })

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            // Clickable text if user needs to register
            Text(text = "Don't have an account? ", color = TextWhite)


            Text(
                text = "Register",
                color = Accent,
                modifier = Modifier.clickable {
                    onRegisterClick()
                },
            )
        }


    }

}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    CapoCoinAppTheme {
        Login()
    }
}

