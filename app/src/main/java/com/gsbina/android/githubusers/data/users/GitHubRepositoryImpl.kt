package com.gsbina.android.githubusers.data.users

class GitHubRepositoryImpl(
    private val gitHubService: GitHubService
): GitHubRepository {
    override suspend fun getUsers(since: Int, perPage: Int): List<GitHubUser> {
        return gitHubService.getUsers(since, perPage)
    }
}