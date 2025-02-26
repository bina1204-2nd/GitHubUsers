package com.gsbina.android.githubusers.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsbina.android.githubusers.domain.users.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UsersUiState(emptyList()))
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()

    fun getUsers() {
        viewModelScope.launch {
            runCatching { getUsersUseCase() }
                .onSuccess { _uiState.value = UsersUiState(it) }
                .onFailure { _uiState.value = UsersUiState(emptyList(), it.message) }
        }
    }
}
