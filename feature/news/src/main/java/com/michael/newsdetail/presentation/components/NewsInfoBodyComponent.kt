package com.michael.newsdetail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.michael.common.toReadableDate
import com.michael.feature.news.R
import com.michael.newsdetail.presentation.model.NewsDetailUiModel
import com.michael.ui.utils.boldTexStyle
import com.michael.ui.utils.mediumTexStyle


@Composable
internal fun NewsInfoBodyComponent(modifier: Modifier = Modifier, newsDetail: NewsDetailUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), // Adding padding
    ) {
        NewsInfoRowComponent(title = stringResource(R.string.keywords), values = newsDetail.keywords)
        NewsInfoRowComponent(title = stringResource(R.string.creators), values = newsDetail.creator)
        NewsInfoRowComponent(title = stringResource(R.string.categories), values = newsDetail.category)
        NewsInfoRowComponent(title = stringResource(R.string.published_by), values = newsDetail.creator)
        NewsInfoRowComponent(title = stringResource(R.string.published_on), value = newsDetail.pubDate.toReadableDate())
        Text(
            text = stringResource(R.string.description),
            style = boldTexStyle(16),
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = newsDetail.description,
            style = mediumTexStyle(size = 14),
            modifier = Modifier.padding(top = 8.dp),
            color = Color.Gray,
            lineHeight = 20.sp,
            overflow = TextOverflow.Ellipsis,
        )
    }
}


@Composable
private fun NewsInfoRowComponent(
    modifier: Modifier = Modifier,
    title: String,
    value: String? = null,
    values: List<String>? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(
            text = "$title:",
            style = boldTexStyle(16),
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (values != null) {
            values.forEach {
                Text(
                    text = it,
                    style = mediumTexStyle(size = 14),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else if (value != null)
            Text(
                text = value,
                style = mediumTexStyle(size = 14),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
    }
}
