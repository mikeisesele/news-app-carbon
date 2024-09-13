package com.michael.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.michael.base.contract.BaseState
import com.michael.base.model.MessageState
import com.michael.ui.extensions.clickable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    topAppBarContent: @Composable() (() -> Unit)? = null,
    showBackIcon: Boolean = false,
    trailingIconOne: @Composable() (() -> Unit)? = null,
    state: BaseState,
    refreshScreen: (() -> Unit)? = null,
    onSystemBackClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    BackHandler {
        onSystemBackClick?.invoke()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (topAppBarContent != null) {
                TopAppBar(
                    modifier = Modifier.shadow(8.dp, shape = RectangleShape),
                    title = topAppBarContent,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    navigationIcon = {
                        if (showBackIcon) {
                            Icon(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(24.dp)
                                    .clickable(onClick = onBackClick),
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    },
                    actions = {
                        trailingIconOne?.invoke()
                    },
                )
            }
        },
        contentColor = Color.White.copy(alpha = 0.8f),
    ) { paddingValues ->
        if (state.isLoading) {
            CenteredColumn {
                LoadingAnimation()
            }
        }
    else {
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(paddingValues)
            ) {
                content(paddingValues)
            }
        }

    }
}
