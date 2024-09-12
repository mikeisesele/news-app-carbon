package com.michael.news.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michael.ui.utils.boldTexStyle

@Composable
fun ToolBarTitleComponent(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(start = 8.dp),
        style = boldTexStyle(size = 16),
        color = MaterialTheme.colorScheme.onPrimary
    )
}