package com.michael.news.domain.model

data class NewsFeedUiModel(
    val id: Int,
    val articleId: String,
    val category: List<String>,
    val country: List<String>,
    val creator: List<String>,
    val description: String,
    val imageUrl: String,
    val keywords: List<String>,
    val link: String,
    val pubDate: String,
    val sentiment: String,
    val sentimentStats: String,
    val sourceIcon: String,
    val sourceName: String,
    val sourceUrl: String,
    val title: String,
)
