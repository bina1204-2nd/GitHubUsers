package com.gsbina.android.githubusers.data.users

import retrofit2.http.GET

interface GitHubService {
    @GET("users")
    fun getUsers(): List<GitHubUser>
}