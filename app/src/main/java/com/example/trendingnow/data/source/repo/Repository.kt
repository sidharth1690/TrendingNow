package com.example.trendingnow.data.source.repo

import com.example.trendingnow.data.source.network.NetworkHelper
import javax.inject.Inject

class Repository @Inject constructor(private val networkHelper: NetworkHelper) {
    suspend fun getGitRepoData() = networkHelper.getTrendingGitRepo()
    suspend fun fetchRepoData() = networkHelper.fetchTrendingGitRepo()
}