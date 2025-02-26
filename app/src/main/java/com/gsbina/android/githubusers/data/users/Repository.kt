package com.gsbina.android.githubusers.data.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    val id: Int,
    val name: String,
    @SerialName("html_url") val htmlUrl: String,
    val description: String? = null,
    val language: String? = null,
    @SerialName("stargazers_count") val stargazersCount: Int? = null,
    @SerialName("forks_count") val forksCount: Int? = null
)