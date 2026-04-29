package com.example.capocoinapp.designUI.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */
@Composable
fun CalculatorButtonDesign(
    symbol: String,
    modifier: Modifier,
    onClick: () -> Unit
){
    // Calculator button design
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable{ onClick() }
            .then(modifier)
    ){
        // Text for button
        Text(
            text = symbol,
            fontSize = 22.sp,
            color = Color.White
        )
    }
}


