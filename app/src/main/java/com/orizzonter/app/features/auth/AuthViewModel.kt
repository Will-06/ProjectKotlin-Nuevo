package com.orizzonter.app.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orizzonter.app.features.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) = viewModelScope.launch {
        _authState.value = if (repository.login(email, password)) {
            AuthState.Success
        } else {
            AuthState.Error("Credenciales inválidas")
        }
    }

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        repository.register(name, email, password)
        _authState.value = AuthState.Registered
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Success : AuthState()
    object Registered : AuthState()
    data class Error(val message: String) : AuthState()
}
