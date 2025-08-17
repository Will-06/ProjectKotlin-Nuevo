package com.orizzonter.app.features.auth.data

import com.orizzonter.app.features.domain.AuthRepository

class LocalAuthRepository(private val prefs: AuthPreferences) : AuthRepository {

    override suspend fun login(email: String, password: String): Boolean {
        val (_, savedEmail, savedPassword) = prefs.getSavedCredentials()
        return if (email == savedEmail && password == savedPassword) {
            prefs.setLoggedIn(true)
            true
        } else {
            false
        }
    }

    override suspend fun register(name: String, email: String, password: String) {
        prefs.saveUser(name, email, password)
        prefs.setLoggedIn(true)
    }

    override suspend fun isLoggedIn(): Boolean = prefs.isLoggedIn()

    override suspend fun setLoggedIn(loggedIn: Boolean) {
        prefs.setLoggedIn(loggedIn)
    }
}
