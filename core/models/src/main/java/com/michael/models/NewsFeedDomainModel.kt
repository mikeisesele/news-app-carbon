package com.michael.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_feed")
data class NewsFeedDomainModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val aiOrg: String? = null,
    val aiRegion: String? = null,
    val aiTag: String? = null,
    val articleId: String? = null,
    val category: List<String>? = null,
    val content: String? = null,
    val country: List<String>? = null,
    val creator: List<String>? = null,
    val description: String? = null,
    val duplicate: Boolean? = null,
    val imageUrl: String? = null,
    val keywords: List<String>? = null,
    val language: String? = null,
    val link: String? = null,
    val pubDate: String? = null,
    val pubDateTZ: String? = null,
    val sentiment: String? = null,
    val sentimentStats: String? = null,
    val sourceIcon: String? = null,
    val sourceId: String? = null,
    val sourceName: String? = null,
    val sourcePriority: Int? = null,
    val sourceUrl: String? = null,
    val title: String? = null,
)
