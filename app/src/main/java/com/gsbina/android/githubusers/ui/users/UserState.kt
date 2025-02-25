package com.gsbina.android.githubusers.ui.users

import android.net.Uri
import com.gsbina.android.githubusers.data.users.GitHubUser

data class UserState(
    val icon: Uri,
    val login: String
) {
    constructor(user: GitHubUser) : this(
        Uri.parse(user.avatarUrl),
        user.login
    )
}
