package com.gsbina.android.githubusers.ui.users.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsbina.android.githubusers.data.users.GitHubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(private val gitHubRepository: GitHubRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(UserDetailState())
    val uiState: StateFlow<UserDetailState> = _uiState.asStateFlow()

    fun fetchUserDetail(username: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val user = gitHubRepository.getUser(username)
                val repositories = gitHubRepository.getRepositories(username)
                _uiState.update {
                    it.copy(
                        user = user,
                        repositories = repositories,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Failed to load user detail: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }
}