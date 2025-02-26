package com.gsbina.android.githubusers.ui.users.repos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gsbina.android.githubusers.data.users.GitHubUser
import com.gsbina.android.githubusers.data.users.Repository
import com.gsbina.android.githubusers.ui.theme.GitHubUsersTheme

@Composable
fun UserDetailScreen(viewModel: UserViewModel, username: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(username) {
        viewModel.fetchUserDetail(username)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(text = "Error: ${uiState.error}")
        } else {
            UserDetailContent(uiState.user, uiState.repositories)
        }
    }
}

@Composable
fun UserDetailContent(user: GitHubUser?, repositories: List<Repository>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            if (user != null) {
                UserHeader(user)
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Repositories",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(repositories) { repository ->
            RepositoryItem(repository)
        }
    }
}

@Composable
fun UserHeader(user: GitHubUser) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val contentDescription = "User Avatar"
        val modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
        if (LocalInspectionMode.current) {
            // Preview時
            Image(
                painter = ColorPainter(color = Color.Red),
                contentDescription = contentDescription,
                modifier = modifier
            )
        } else {
            // 通常時
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = contentDescription,
                modifier = modifier
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = user.login, style = MaterialTheme.typography.headlineSmall)
        Text(text = user.name, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Followers: ${user.followers}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = "Following: ${user.following}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun RepositoryItem(repository: Repository) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                uriHandler.openUri(repository.htmlUrl)
            }
            .padding(16.dp)
    ) {
        Text(text = repository.name, fontWeight = FontWeight.Bold)
        if (repository.description != null) {
            Text(text = repository.description)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            if (repository.language != null) {
                Text(
                    text = "Language: ${repository.language}",
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            if (repository.stargazersCount != null) {
                Text(text = "Stars: ${repository.stargazersCount}")
            }
        }
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
    }
}

@Preview
@Composable
fun UserDetailContentPreview() {
    GitHubUsersTheme {
        UserDetailContent(
            user = GitHubUser(
                id = 1,
                login = "octocat",
                avatarUrl = "https://github.com/images/error/octocat_happy.gif",
                name = "The Octocat",
                followers = 1000,
                following = 20
            ),
            repositories = listOf(
                Repository(
                    id = 123,
                    name = "repo1",
                    htmlUrl = "https://github.com/octocat/repo1",
                    description = "A test repository",
                    language = "Kotlin",
                    stargazersCount = 10,
                    forksCount = 2
                ),
                Repository(
                    id = 456,
                    name = "repo2",
                    htmlUrl = "https://github.com/octocat/repo2",
                    description = "Another test repository",
                    language = "Java",
                    stargazersCount = 5,
                    forksCount = 1
                )
            )
        )
    }
}