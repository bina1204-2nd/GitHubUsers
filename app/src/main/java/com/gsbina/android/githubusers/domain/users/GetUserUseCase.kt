package com.gsbina.android.githubusers.domain.users

import com.gsbina.android.githubusers.data.users.GitHubRepository
import com.gsbina.android.githubusers.data.users.GitHubUserDetail

class GetUserUseCase(private val repository: GitHubRepository) {
    suspend operator fun invoke(userName: String):  GitHubUserDetail {
        return GitHubUserDetail(
            user = repository.getUser(userName),
            repositories = repository.getRepositories(userName)
        )
    }
}