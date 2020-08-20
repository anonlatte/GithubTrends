package com.anonlatte.trends.repository

import com.anonlatte.trends.api.GithubService
import com.anonlatte.trends.db.dao.RepositoriesDao
import com.anonlatte.trends.db.model.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private var githubService: GithubService,
    private var repositoriesDao: RepositoriesDao
) : GithubRepository {
    override suspend fun getTopRepositories(sinceType: String): List<Repository> {
        CoroutineScope(Dispatchers.IO).launch {
            val retrievedRepositories: List<Repository>? = try {
                githubService.getTopHeadlines(since = sinceType)
            } catch (exception: Throwable) {
                Timber.tag("network").d("$exception")
                exception.printStackTrace()
                null
            }
            if (!retrievedRepositories.isNullOrEmpty()) {
                retrievedRepositories.forEach {
                    it.sinceType = sinceType
                }
                repositoriesDao.multipleInsert(retrievedRepositories)
            }
        }.join()
        return repositoriesDao.getRepositories()
    }
}