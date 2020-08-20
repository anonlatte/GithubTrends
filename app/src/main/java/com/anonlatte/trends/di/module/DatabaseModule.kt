package com.anonlatte.trends.di.module

import android.app.Application
import androidx.room.Room
import com.anonlatte.trends.db.RepositoriesDatabase
import com.anonlatte.trends.db.dao.RepositoriesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    private lateinit var database: RepositoriesDatabase

    @Singleton
    @Provides
    fun providesDatabase(application: Application): RepositoriesDatabase {
        database =
            Room.databaseBuilder(application, RepositoriesDatabase::class.java, "repositories-db")
                .build()
        return database
    }

    @Singleton
    @Provides
    fun providesRepositoriesDao(database: RepositoriesDatabase): RepositoriesDao =
        database.repositoriesDao()
}