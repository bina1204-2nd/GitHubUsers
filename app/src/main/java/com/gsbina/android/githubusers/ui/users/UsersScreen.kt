package com.gsbina.android.githubusers.ui.users

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import com.gsbina.android.githubusers.data.ApiModule
import com.gsbina.android.githubusers.data.users.GitHubRepositoryImpl
import com.gsbina.android.githubusers.data.users.GitHubUser
import com.gsbina.android.githubusers.domain.users.GetUsersUseCase
import com.gsbina.android.githubusers.ui.theme.GitHubUsersTheme

@Composable
fun UsersScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: UsersViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn(modifier = modifier.then(Modifier.fillMaxWidth().fillMaxHeight().background(Color.DarkGray))) {
        items(uiState.users) { user ->
            UserView(state = UserState(user))
        }
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                viewModel.getUsers()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun UserView(state: UserState) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)) {
        val contentDescription = "User Avatar"
        val modifier = Modifier
            .size(64.dp)
            .padding(8.dp)
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
                model = state.icon,
                contentDescription = contentDescription,
                modifier = modifier
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(state.login, fontSize = 16.sp, color = Color.Black)
        }
    }
}

@Preview
@Composable
fun UsersScreenPreview() {
    GitHubUsersTheme {
        UsersScreen(viewModel = UsersViewModel(GetUsersUseCase(repository = GitHubRepositoryImpl(ApiModule.gitHubService))), modifier = Modifier.padding())
    }
}

@Preview
@Composable
fun UserPreview() {
    GitHubUsersTheme {
        val state = UserState(
            Uri.parse("https://avatars.githubusercontent.com/u/943320?v=4"),
            "bina1204"
        )
        UserView(
            state
        )
    }
}