package com.michael.newsdetail.presentation.model

internal data class NewsDetailUiModel(
    val articleId: String,
    val category: List<String>,
    val creator: List<String>,
    val description: String,
    val imageUrl: String,
    val keywords: List<String>,
    val link: String,
    val pubDate: String,
    val sourceIcon: String,
    val sourceName: String,
    val sourceUrl: String,
    val title: String,
)
