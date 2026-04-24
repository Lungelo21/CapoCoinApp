package com.example.capocoinapp.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.capocoinapp.R

val RobotoSlab = FontFamily(
    Font(R.font.roboto_slab_variable)
)
object CapoType {
    val cardTitle = TextStyle(
        fontSize = 20.sp,
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.Bold,
        color = TextWhite
    )

    val cardSubTitle = TextStyle(
        fontSize = 16.sp,
        fontFamily = RobotoSlab,
        fontWeight = FontWeight.Medium,
        color = SubTextWhite
    )
}