package com.example.trendingnow.ui.main.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingnow.data.source.network.NetworkHelper
import com.example.trendingnow.data.source.model.RepoData
import com.example.trendingnow.data.source.repo.Repository
import com.example.trendingnow.utils.DataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(private val repository: Repository, private val networkHelper: NetworkHelper) :
    ViewModel() {

        private val repoList= MutableLiveData<DataSource<RepoData>>()
         val gitTrendingList : LiveData<DataSource<RepoData>> get ()= repoList

    init {
        fetchTrendingRepos()
    }


    private fun fetchTrendingRepos() {
        viewModelScope.launch {
            repoList.postValue(DataSource.loading(null))
//            if (networkHelper.isNetworkConnected()) {
                repository.fetchRepoData().let {
                    if (it.isSuccessful) {
                        repoList.postValue(DataSource.success(it.body()))
                    } else
                        repoList.postValue(DataSource.error(it.errorBody().toString(), null))
                }
//            } else repoList.postValue(DataSource.error("No internet connection", null))
        }
    }
}