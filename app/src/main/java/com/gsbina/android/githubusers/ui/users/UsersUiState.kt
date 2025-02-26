package com.gsbina.android.githubusers.ui.users

import com.gsbina.android.githubusers.data.users.GitHubUser

data class UsersUiState(
    val users: List<GitHubUser>,
    val error: String? = null
)