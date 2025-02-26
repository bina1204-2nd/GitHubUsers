package com.gsbina.android.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gsbina.android.githubusers.data.ApiModule
import com.gsbina.android.githubusers.data.users.GitHubRepositoryImpl
import com.gsbina.android.githubusers.domain.users.GetUserUseCase
import com.gsbina.android.githubusers.domain.users.GetUsersUseCase
import com.gsbina.android.githubusers.ui.Screen
import com.gsbina.android.githubusers.ui.users.repos.UserDetailScreen
import com.gsbina.android.githubusers.ui.theme.GitHubUsersTheme
import com.gsbina.android.githubusers.ui.users.UsersScreen
import com.gsbina.android.githubusers.ui.users.UsersViewModel
import com.gsbina.android.githubusers.ui.users.repos.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubUsersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val gitHubRepository = GitHubRepositoryImpl(ApiModule.gitHubService)
                    val usersViewModel: UsersViewModel =
                        viewModel { UsersViewModel(GetUsersUseCase(gitHubRepository)) }
                    val userViewModel: UserViewModel =
                        viewModel { UserViewModel(GetUserUseCase(gitHubRepository)) }
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Users.route
                    ) {
                        composable(Screen.Users.route) {
                            UsersScreen(
                                navController = navController,
                                viewModel = usersViewModel
                            )
                        }
                        composable(
                            route = Screen.UserDetail.route,
                            arguments = listOf(navArgument("username") { type =
                                NavType.StringType })
                        ) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            UserDetailScreen(viewModel = userViewModel, username = username)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitHubUsersTheme {
        UsersScreen(navController = rememberNavController(), viewModel = UsersViewModel(GetUsersUseCase(repository = GitHubRepositoryImpl(ApiModule.gitHubService))))
    }
}