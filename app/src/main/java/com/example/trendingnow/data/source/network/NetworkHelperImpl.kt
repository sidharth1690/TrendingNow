package com.example.trendingnow.data.source.network

import com.example.trendingnow.data.source.model.RepoData
import retrofit2.Response
import javax.inject.Inject

class NetworkHelperImpl @Inject constructor(private val networkService: NetworkService):
    NetworkHelper {
    override suspend fun getTrendingGitRepo(): Response<RepoData> =networkService.getTrendingGitRepo()
    override suspend fun fetchTrendingGitRepo(): Response<RepoData> =networkService.getTrendingRepos("language:Kotlin",1,20)

}