package com.anonlatte.trends.ui.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anonlatte.trends.db.model.Repository
import com.anonlatte.trends.db.model.Since
import com.anonlatte.trends.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(private var mainRepository: MainRepository) : ViewModel() {

    var reposMediatorLiveData = MediatorLiveData<List<Repository>>()
    var sinceType: MutableLiveData<String> = MutableLiveData()

    init {
        sinceType = MutableLiveData(Since.DAILY.value)
        reposMediatorLiveData.addSource(sinceType) {
            CoroutineScope(Dispatchers.IO).launch {
                reposMediatorLiveData.postValue(setRepositoriesLiveData(it))
            }
        }
    }

    private suspend fun setRepositoriesLiveData(sinceType: String) =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            mainRepository.getTopRepositories(sinceType)
        }

    var isLoading = MutableLiveData(true)

    fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            reposMediatorLiveData.postValue(setRepositoriesLiveData(sinceType.value!!))
            isLoading.postValue(false)
        }
    }
}