package com.anonlatte.trends.db.dao

import androidx.room.*
import com.anonlatte.trends.db.model.Repository
import com.anonlatte.trends.db.model.Since

@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun singleInsert(repository: Repository): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun multipleInsert(repositories: List<Repository>): List<Long>

    @Query("SELECT * FROM repositories WHERE sinceType=:sinceType")
    suspend fun getRepositories(sinceType: String = Since.DAILY.value): List<Repository>

    @Query("SELECT MAX(indexInResponse) + 1 FROM repositories")
    fun getNextIndex(): Int

    @Delete
    suspend fun delete(repository: Repository): Int

}
