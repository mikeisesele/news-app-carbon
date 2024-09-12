package com.michael.localdata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.michael.models.NewsFeedDomainModel
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {

    @Insert
    suspend fun insertNews(items: List<NewsFeedDomainModel>)

    @Query("SELECT * FROM news_feed")
    suspend fun getNews(): List<NewsFeedDomainModel>

    @Query("SELECT * FROM news_feed WHERE title LIKE :query OR description LIKE :query OR content LIKE :query OR category LIKE :query OR keywords LIKE :query  ORDER BY pubDate DESC")
    suspend fun searchNews(query: String): List<NewsFeedDomainModel>
}