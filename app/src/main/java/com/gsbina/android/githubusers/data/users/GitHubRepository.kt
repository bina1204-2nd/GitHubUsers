package com.gsbina.android.githubusers.data.users

interface GitHubRepository {
    suspend fun getUsers(): List<GitHubUser>
}