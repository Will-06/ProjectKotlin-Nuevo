package com.orizzonter.app.features.auth.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class AuthPreferences(private val context: Context) {

    private object Keys {
        val NAME = stringPreferencesKey("user_name")
        val EMAIL = stringPreferencesKey("user_email")
        val PASSWORD = stringPreferencesKey("user_password")
        val LOGGED_IN = booleanPreferencesKey("logged_in")
    }

    private suspend fun getPrefs() = context.dataStore.data.first()

    suspend fun saveUser(name: String, email: String, password: String) {
        context.dataStore.edit {
            it[Keys.NAME] = name
            it[Keys.EMAIL] = email
            it[Keys.PASSWORD] = password
        }
    }

    suspend fun getSavedCredentials(): Triple<String?, String?, String?> {
        val prefs = getPrefs()
        return Triple(prefs[Keys.NAME], prefs[Keys.EMAIL], prefs[Keys.PASSWORD])
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit {
            it[Keys.LOGGED_IN] = loggedIn
        }
    }

    suspend fun isLoggedIn(): Boolean {
        return getPrefs()[Keys.LOGGED_IN] ?: false
    }

    suspend fun getUserName(): String? = getPrefs()[Keys.NAME]

    suspend fun getUserEmail(): String? = getPrefs()[Keys.EMAIL]

    // NUEVO: Método para limpiar datos de sesión y marcar como deslogueado
    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.NAME)
            prefs.remove(Keys.EMAIL)
            prefs.remove(Keys.PASSWORD)
            prefs[Keys.LOGGED_IN] = false
        }
    }
}
