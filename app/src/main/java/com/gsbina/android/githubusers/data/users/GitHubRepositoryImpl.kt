package com.gsbina.android.githubusers.data.users

class GitHubRepositoryImpl(
    private val gitHubService: GitHubService
): GitHubRepository {
    override suspend fun getUsers(): List<GitHubUser> {
        return gitHubService.getUsers()
    }
}