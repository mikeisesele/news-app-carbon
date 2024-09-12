package com.michael.newsdetail.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.michael.feature.news.R
import com.michael.newsdetail.domain.contract.NewsDetailViewAction
import com.michael.newsdetail.presentation.components.NewsDetailScreenHeader
import com.michael.newsdetail.presentation.components.NewsImageSection
import com.michael.newsdetail.presentation.components.NewsInfoBodyComponent
import com.michael.newsdetail.presentation.components.ZoomableImage
import com.michael.newsdetail.presentation.model.NewsDetailUiModel
import com.michael.ui.components.BaseScreen
import com.michael.ui.components.ToolBarTitleComponent
import com.michael.ui.extensions.clickable
import com.michael.ui.extensions.rememberStateWithLifecycle
import kotlinx.serialization.Serializable

@Serializable
data class NewsDetailScreenDestination(val newsId: Int)

@Composable
fun NewsDetailScreen(newsId: Int, modifier: Modifier = Modifier, onBackClick: () -> Unit) {

    val viewModel: NewsDetailViewModel = hiltViewModel()
    val state by rememberStateWithLifecycle(viewModel.state)
    var isFullScreen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onViewAction(NewsDetailViewAction.GetNewsDetail(newsId))
    }

    BaseScreen(
        modifier = modifier,
        state = state,
        onBackClick = {
            handleOnBackClick(
                isFullScreen = isFullScreen,
                onBackClick = onBackClick,
                updateFullScreen = { isFullScreen = it }
            )
        },
        onSystemBackClick = {
            handleOnBackClick(
                isFullScreen = isFullScreen,
                onBackClick = onBackClick,
                updateFullScreen = { isFullScreen = it }
            )
        },
        showBackIcon = true,
        topAppBarContent = {
            ToolBarTitleComponent(text = stringResource(R.string.news_detail))
        }
    ) {

        state.newsDetail?.let { detail ->
            NewsDetailComponent(
                newsDetail = detail,
                isFullScreen = isFullScreen,
                onImageClick = {
                    isFullScreen = it
                }
            )
        }
    }
}

fun handleOnBackClick(
    isFullScreen: Boolean,
    onBackClick: () -> Unit,
    updateFullScreen: (Boolean) -> Unit = {}
) {
    if (isFullScreen) {
        updateFullScreen(false)
    } else {
        onBackClick()
    }
}

@Composable
private fun NewsDetailComponent(
    newsDetail: NewsDetailUiModel,
    isFullScreen: Boolean,
    onImageClick: (Boolean) -> Unit
) = with(newsDetail) {

    val scrollState = rememberScrollState()

    Box {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            NewsDetailScreenHeader(title = title, sourceIconUrl = sourceUrl)
            NewsImageSection(
                news = newsDetail,
                onImageClick = { onImageClick(true) }
            )
            NewsInfoBodyComponent(
                newsDetail = newsDetail
            )
        }
    }
    // Full-Screen Image Overlay
    AnimatedVisibility(
        visible = isFullScreen,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + scaleIn(initialScale = 0.9f),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + scaleOut(targetScale = 0.9f),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
                .clickable { onImageClick(false) },
            contentAlignment = Alignment.Center
        ) {
            ZoomableImage(
                imageUrl = imageUrl,
                cornerRadius = 16
            )
        }
    }
}

