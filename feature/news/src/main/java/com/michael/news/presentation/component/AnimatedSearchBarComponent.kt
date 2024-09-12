package com.michael.news.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michael.ui.extensions.clickable


@Composable
internal fun AnimatedSearchBarComponent(
    searchQuery: String,
    searchBarComponentVisible: Boolean = false,
    onCloseSearchClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    AnimatedVisibility(
        visible = searchBarComponentVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + scaleIn(
            initialScale = 0.9f
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + scaleOut(
            targetScale = 0.9f
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SearchBarComponent(
                modifier = Modifier.weight(0.8f),
                onValueChange = onValueChange,
                searchQuery = searchQuery,
                onSearch = onSearch
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(0.1f)
                    .clickable(
                        onClick = onCloseSearchClick
                    ),
                imageVector = Icons.Default.Close,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
