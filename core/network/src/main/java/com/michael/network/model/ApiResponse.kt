package com.michael.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class ApiResponse(
    @SerialName("status") val status: String,
    @SerialName("totalResults") val totalResults: Int,
    @SerialName("results") val results: List<ArticleDTO>
)

@Serializable
data class ArticleDTO(
    @SerialName("article_id") val articleId: String,
    @SerialName("title") val title: String,
    @SerialName("link") val link: String,
    @SerialName("source") val source: String,
    @SerialName("date_published") val datePublished: String
)