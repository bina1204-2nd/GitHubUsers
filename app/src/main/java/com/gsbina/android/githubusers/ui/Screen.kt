package com.gsbina.android.githubusers.ui

sealed class Screen(val route: String) {
    data object Users : Screen("users")
    data object UserDetail : Screen("user_detail/{username}") {
        fun createRoute(username: String) = "user_detail/$username"
    }
}