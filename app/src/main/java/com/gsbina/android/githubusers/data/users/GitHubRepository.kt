package com.gsbina.android.githubusers.data.users

interface GitHubRepository {
    /** ユーザー一覧を取得する */
    suspend fun getUsers(since: Int, perPage: Int): List<GitHubUser>

    /** username のユーザーを取得する */
    suspend fun getUser(username: String): GitHubUser

    /** username のリポジトリ一覧を取得する */
    suspend fun getRepositories(username: String): List<Repository>
}
