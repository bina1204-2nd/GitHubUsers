package com.gsbina.android.githubusers.data.users

import com.gsbina.android.githubusers.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer ${BuildConfig.GITHUB_TOKEN}",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("users")
    suspend fun getUsers(@Query("since") since: Int, @Query("per_page") perPage: Int): List<GitHubUser>

    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer ${BuildConfig.GITHUB_TOKEN}",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("users/{userName}")
    suspend fun getUser(@Path("userName") userName: String): GitHubUser

    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer ${BuildConfig.GITHUB_TOKEN}",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("users/{userName}/repos")
    suspend fun getRepositories(@Path("userName") userName: String): List<Repository>
}