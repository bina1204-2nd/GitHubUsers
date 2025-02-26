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

    fun getUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            runCatching {
                val maxUserId = _uiState.value.users.maxOfOrNull { it.id } ?: 0
                getUsersUseCase(since = maxUserId)
            }
                .onSuccess { users ->
                    _uiState.update { it.copy(users = it.users + users, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(users = emptyList(), error = error.message, isLoading = false)
                    }
                }
        }
    }
}
