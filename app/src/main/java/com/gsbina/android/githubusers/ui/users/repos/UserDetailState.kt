package com.gsbina.android.githubusers.ui.users.repos

import com.gsbina.android.githubusers.data.users.GitHubUser
import com.gsbina.android.githubusers.data.users.Repository

data class UserDetailState(
    val user: GitHubUser? = null,
    val repositories: List<Repository> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)