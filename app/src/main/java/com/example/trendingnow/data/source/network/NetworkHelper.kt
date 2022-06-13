package com.example.trendingnow.data.source.network

import com.example.trendingnow.data.source.model.RepoData
import retrofit2.Response

interface NetworkHelper {
    suspend fun getTrendingGitRepo(): Response<RepoData>
    suspend fun fetchTrendingGitRepo(): Response<RepoData>
}