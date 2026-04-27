package com.example.capocoinapp.designUI.components

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.Primary

@Composable
fun PrimaryButton(
    buttonText: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary
        )
    ) {
        Text(
            text = buttonText,
            style = CapoType.cardTitle,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    CapoCoinAppTheme {
        PrimaryButton("Add Transaction", true) {}
    }
}