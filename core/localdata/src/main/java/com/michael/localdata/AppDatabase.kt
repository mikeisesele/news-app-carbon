package com.michael.localdata

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.michael.localdata.dao.NewsDao
import com.michael.localdata.typeconverter.Converters
import com.michael.models.NewsFeedDomainModel

const val DB_NAME = "carbon_news_database"

@Database(entities = [NewsFeedDomainModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDao
    companion object {
        const val DATABASE_NAME = DB_NAME
    }
}
