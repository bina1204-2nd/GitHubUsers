package com.gsbina.android.githubusers.data.users

data class GitHubUserDetail(
    val user: GitHubUser,
    val repositories: List<Repository>
)