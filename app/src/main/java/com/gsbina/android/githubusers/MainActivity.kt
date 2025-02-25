package com.gsbina.android.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gsbina.android.githubusers.data.users.GitHubUser
import com.gsbina.android.githubusers.ui.theme.GitHubUsersTheme
import com.gsbina.android.githubusers.ui.users.UsersScreen
import com.gsbina.android.githubusers.ui.users.UsersState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubUsersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val state = UsersState(
                        listOf(
                            GitHubUser(
                                id = 1,
                                login = "bina1204",
                                avatarUrl = "https://avatars.githubusercontent.com/u/943320?v=4"
                            ),
                            GitHubUser(
                                id = 2,
                                login = "octocat",
                                avatarUrl = "https://avatars.githubusercontent.com/u/943320?v=4"
                            )
                        )
                    )
                    UsersScreen(state = state, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitHubUsersTheme {
        val state = UsersState(
            listOf(
                GitHubUser(
                    id = 1,
                    login = "bina1204",
                    avatarUrl = "https://avatars.githubusercontent.com/u/943320?v=4"
                ),
                GitHubUser(
                    id = 2,
                    login = "octocat",
                    avatarUrl = "https://avatars.githubusercontent.com/u/943320?v=4"
                )
            )
        )
        UsersScreen(state = state)
    }
}