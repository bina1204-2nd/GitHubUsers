package com.gsbina.android.githubusers.domain.users

import com.gsbina.android.githubusers.data.users.GitHubRepository
import com.gsbina.android.githubusers.data.users.GitHubUser

/** ユーザー一覧を取得する */
class GetUsersUseCase(private val repository: GitHubRepository) {
    suspend operator fun invoke(since: Int = 0, perPage: Int = 30): List<GitHubUser> {
        return repository.getUsers(since, perPage)
    }
}