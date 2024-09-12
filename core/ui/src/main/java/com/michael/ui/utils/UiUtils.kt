package com.michael.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun boldTexStyle(size: Int, color: Color = Color.Black): TextStyle {
    return TextStyle(
        color = color,
        fontSize = size.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun mediumTexStyle(size: Int, color: Color = Color.Black): TextStyle {
    return TextStyle(
        color = color,
        fontSize = size.sp,
        fontWeight = FontWeight.Medium,
    )
}