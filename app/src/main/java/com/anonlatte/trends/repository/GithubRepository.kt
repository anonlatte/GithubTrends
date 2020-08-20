package com.anonlatte.trends.repository

import com.anonlatte.trends.db.model.Repository
import com.anonlatte.trends.db.model.Since

interface GithubRepository {
    suspend fun getTopRepositories(sinceType: String = Since.DAILY.value): List<Repository>
}
