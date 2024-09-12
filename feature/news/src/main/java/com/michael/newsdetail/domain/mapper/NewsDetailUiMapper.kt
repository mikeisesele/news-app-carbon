package com.michael.newsdetail.domain.mapper

import com.michael.models.NewsFeedDomainModel
import com.michael.news.presentation.model.NewsFeedUiModel
import com.michael.newsdetail.presentation.model.NewsDetailUiModel


internal fun NewsFeedDomainModel.toDetailUiModel(): NewsDetailUiModel {
    return with(this) {
        NewsDetailUiModel(
            id = id,
            category = category.orEmpty(),
            creator = creator.orEmpty(),
            description = description.orEmpty(),
            imageUrl = imageUrl.orEmpty(),
            keywords = keywords.orEmpty(),
            pubDate = pubDate.orEmpty(),
            title = title.orEmpty(),
            link = link.orEmpty(),
            sourceUrl = sourceUrl.orEmpty(),
            sourceIcon = sourceIcon.orEmpty(),
            sourceName = sourceName.orEmpty(),
        )
    }
}
