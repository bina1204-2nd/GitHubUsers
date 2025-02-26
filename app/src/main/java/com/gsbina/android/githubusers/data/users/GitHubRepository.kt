package com.gsbina.android.githubusers.data.users

interface GitHubRepository {
    suspend fun getUsers(since: Int, perPage: Int): List<GitHubUser>

    suspend fun getUser(username: String): GitHubUser

    suspend fun getRepositories(username: String): List<Repository>
}
