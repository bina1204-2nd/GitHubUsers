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
import com.gsbina.android.githubusers.data.ApiModule
import com.gsbina.android.githubusers.data.users.GitHubRepositoryImpl
import com.gsbina.android.githubusers.data.users.GitHubUser
import com.gsbina.android.githubusers.domain.users.GetUsersUseCase
import com.gsbina.android.githubusers.ui.theme.GitHubUsersTheme
import com.gsbina.android.githubusers.ui.users.UsersScreen
import com.gsbina.android.githubusers.ui.users.UsersUiState
import com.gsbina.android.githubusers.ui.users.UsersViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubUsersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UsersScreen(
                        viewModel = UsersViewModel(GetUsersUseCase(GitHubRepositoryImpl(ApiModule.gitHubService))),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitHubUsersTheme {
        UsersScreen(viewModel = UsersViewModel(GetUsersUseCase(repository = GitHubRepositoryImpl(ApiModule.gitHubService))))
    }
}