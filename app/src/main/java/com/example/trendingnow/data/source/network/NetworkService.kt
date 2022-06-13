package com.example.trendingnow.data.source.network

import com.example.trendingnow.data.source.model.RepoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("trending")
    suspend fun getTrendingGitRepo(): Response<RepoData>

    @GET("search/repositories?sort=stars")
    suspend fun getTrendingRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Response<RepoData>

}