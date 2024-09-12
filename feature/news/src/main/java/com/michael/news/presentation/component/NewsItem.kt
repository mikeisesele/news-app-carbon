package com.michael.news.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michael.news.domain.model.NewsFeedUiModel
import com.michael.ui.utils.boldTexStyle
import com.michael.ui.utils.mediumTexStyle

@Composable
internal fun NewsItem(article: NewsFeedUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)) {
            Text(text = article.title, style = boldTexStyle(size = 16))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.description, style = mediumTexStyle(size = 12))
        }
    }
}