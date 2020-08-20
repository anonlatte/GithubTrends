package com.anonlatte.trends.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anonlatte.trends.db.dao.RepositoriesDao
import com.anonlatte.trends.db.model.Repository

@Database(
    entities = [Repository::class],
    version = RepositoriesDatabase.VERSION,
    exportSchema = false
)
abstract class RepositoriesDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun repositoriesDao(): RepositoriesDao
}