package com.michael.news.domain.mappers

import com.michael.common.ImmutableList
import com.michael.common.toImmutableList
import com.michael.models.NewsFeedDomainModel
import com.michael.news.presentation.model.NewsFeedUiModel


internal fun List<NewsFeedDomainModel>.toUiModel(): ImmutableList<NewsFeedUiModel> =
    map { it.toUiModel() }.toImmutableList()


internal fun NewsFeedDomainModel.toUiModel(): NewsFeedUiModel {
    return with(this) {
        NewsFeedUiModel(
            category = category.orEmpty(),
            creator = creator.orEmpty(),
            description = description.orEmpty(),
            imageUrl = imageUrl.orEmpty(),
            keywords = keywords.orEmpty(),
            pubDate = pubDate.orEmpty(),
            title = title.orEmpty(),
            articleId = articleId
        )
    }
}
