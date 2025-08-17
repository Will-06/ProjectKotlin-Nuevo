package com.orizzonter.app.features.domain

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(name: String, email: String, password: String)
    suspend fun isLoggedIn(): Boolean
    suspend fun setLoggedIn(loggedIn: Boolean)
}
