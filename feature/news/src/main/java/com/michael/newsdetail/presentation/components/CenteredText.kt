package com.michael.newsdetail.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.michael.ui.components.CenteredColumn
import com.michael.ui.utils.boldTexStyle

@Composable
fun CenteredText(
    text: String, onCenteredTextAction: (() -> Unit)? = null
) {
    CenteredColumn {
        Text(
            text = text, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = boldTexStyle(size = 14)
        )



        if (onCenteredTextAction != null) {
            Button(onClick = onCenteredTextAction) {
                Text(text = "Retry")
            }
        }
    }
}