package com.michael.localdata

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

const val DB_NAME = "app_database"

@Database(entities = [FakeModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = DB_NAME
    }
}

@Entity
data class FakeModel(
    @PrimaryKey
    val id: Long,
    val name: String,
)
