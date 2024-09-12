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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.michael.common.toReadableDate
import com.michael.news.presentation.model.NewsFeedUiModel
import com.michael.ui.extensions.applyIf
import com.michael.ui.extensions.clickable
import com.michael.ui.utils.boldTexStyle
import com.michael.ui.utils.mediumTexStyle

@Composable
internal fun NewsItem(article: NewsFeedUiModel, onNewsCardClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onNewsCardClick(article.id) }
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Text(text = article.title, style = boldTexStyle(size = 16))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.description, style = mediumTexStyle(size = 12), maxLines = 3)
            if (article.creator.isNotEmpty()) {
                Text(
                    text = formatCreators(article.creator),
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    style = boldTexStyle(size = 10)
                )
            }
            Text(
                text = if (article.creator.isNotEmpty()) "on ${article.pubDate.toReadableDate()}" else article.pubDate.toReadableDate(),
                textAlign = TextAlign.End,
                style = boldTexStyle(size = 10),
                modifier = Modifier
                    .fillMaxWidth()
                    .applyIf(article.creator.isEmpty()) {
                        padding(top = 8.dp)
                    }
            )
        }
    }
}

private fun formatCreators(creators: List<String>): String {
    return when {
        creators.isEmpty() -> "Created by Unknown" // Handle empty list case
        creators.size == 1 -> "Created by ${creators[0]}"
        creators.size == 2 -> "Created by ${creators[0]} and ${creators[1]}"
        else -> {
            // Join all but the last with commas, and the last one with "and"
            val firstPart = creators.dropLast(1).joinToString(", ")
            val lastPart = creators.last()
            "Created by $firstPart, and $lastPart"
        }
    }
}
