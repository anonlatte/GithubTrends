package com.anonlatte.trends.api

import com.anonlatte.trends.db.model.Repository
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("/repositories")
    suspend fun getTopHeadlines(
        @Query("language") language: String = "kotlin",
        @Query("since") since: String = "daily",
    ): List<Repository>?
}