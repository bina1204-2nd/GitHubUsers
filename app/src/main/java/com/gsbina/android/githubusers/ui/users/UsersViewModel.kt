package com.gsbina.android.githubusers.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsbina.android.githubusers.domain.users.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UsersUiState(emptyList()))
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()
    private var since: Int = 0

    fun getUsers() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = since == 0,
                    isPaginating = since > 0,
                    error = null
                )
            }
            runCatching {
                val maxUserId = _uiState.value.users.maxOfOrNull { it.id } ?: 0
                getUsersUseCase(since = maxUserId)
            }
                .onSuccess { users ->
                    _uiState.update {
                        it.copy(
                            users = it.users + users,
                            isLoading = false,
                            isPaginating = false
                        )
                    }
                    since = users.last().id
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message,
                            isLoading = false,
                            isPaginating = false
                        )
                    }
                }
        }
    }

    fun filterUsers(query: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val filteredUsers = if (query.isBlank()) {
                    emptyList()
                } else {
                    currentState.users.filter { user ->
                        user.login.contains(query, ignoreCase = true) ||
                                user.name.contains(query, ignoreCase = true)
                    }
                }
                currentState.copy(filteredUsers = filteredUsers)
            }
        }
    }
}
