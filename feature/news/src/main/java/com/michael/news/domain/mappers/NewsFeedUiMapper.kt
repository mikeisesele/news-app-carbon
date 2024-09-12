package com.michael.news.domain.mappers

import com.michael.common.ImmutableList
import com.michael.common.toImmutableList
import com.michael.models.NewsFeedDomainModel
import com.michael.news.domain.model.NewsFeedUiModel


fun List<NewsFeedDomainModel>.toUiModel(): ImmutableList<NewsFeedUiModel> =
    map { it.toUiModel() }.toImmutableList()


fun NewsFeedDomainModel.toUiModel(): NewsFeedUiModel {
    return with(this) {
        NewsFeedUiModel(
            id = id,
            articleId = articleId.orEmpty(),
            category = category.orEmpty(),
            country = country.orEmpty(),
            creator = creator.orEmpty(),
            description = description.orEmpty(),
            imageUrl = imageUrl.orEmpty(),
            keywords = keywords.orEmpty(),
            link = link.orEmpty(),
            pubDate = pubDate.orEmpty(),
            sentiment = sentiment.orEmpty(),
            sentimentStats = sentimentStats.orEmpty(),
            sourceIcon = sourceIcon.orEmpty(),
            sourceName = sourceName.orEmpty(),
            sourceUrl = sourceUrl.orEmpty(),
            title = title.orEmpty(),
        )
    }
}
