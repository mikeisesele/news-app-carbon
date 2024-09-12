package com.michael.newsdetail.presentation.components

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.michael.feature.news.R
import kotlin.math.max
import kotlin.math.min


@Composable
internal fun ZoomableImage(
    imageUrl: Any,
    modifier: Modifier = Modifier,
    cornerRadius: Int
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var lastScale by remember { mutableFloatStateOf(1f) }
    var lastOffset by remember { mutableStateOf(Offset.Zero) }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .build(),
        contentDescription = stringResource(R.string.zoomable_image),
        contentScale = ContentScale.Fit,
        modifier = modifier
            .graphicsLayer(
                scaleX = max(1f, min(3f, scale)),
                scaleY = max(1f, min(3f, scale)),
                translationX = offset.x,
                translationY = offset.y
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (lastScale * zoom).coerceIn(1f, 3f)
                    offset = lastOffset + pan * scale // Adjust offset based on current scale
                    lastScale = scale // Update lastScale for next gesture
                    lastOffset = offset // Update lastOffset for next gesture
                }
            }
            .clip(RoundedCornerShape(cornerRadius.dp))
            .fillMaxSize()
    )
}
