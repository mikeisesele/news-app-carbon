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

}