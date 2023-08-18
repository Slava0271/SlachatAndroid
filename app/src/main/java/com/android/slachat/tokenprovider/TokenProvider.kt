package com.android.slachat.tokenprovider

import android.content.Context
import android.content.SharedPreferences
import clean.android.network.auth.ApiTokenProvider
import java.io.IOException

class TokenProvider(private val context: Context) : ApiTokenProvider {

    private val preferenceStorage: SharedPreferences by lazy {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    private var viewWithoutAuth = false

    override fun hasToken(): Boolean = getToken().isNotEmpty()
    override fun hasRefreshToken(): Boolean = getRefreshToken().isNotEmpty()
    override fun getTokenTime(): Long = get(PREFS_TOKEN_END, 0L)

    override fun saveToken(accessToken: String, refreshToken: String) {
        put(PREFS_TOKEN, accessToken)
        put(PREFS_TOKEN_REFRESH, refreshToken)
    }

    override fun saveAuthState(state: Boolean) {
        viewWithoutAuth = state
    }

    override fun getAuthState(): Boolean {
        return viewWithoutAuth
    }

    override fun getToken(): String = get(PREFS_TOKEN, "")

    override fun getRefreshToken(): String = get(PREFS_TOKEN_REFRESH, "")

    override fun clear() {
        saveToken("", "")
    }

    override fun saveTermsStatus(status: Boolean) {
        put(TERMS, status)
    }

    override fun getTermsStatus(): Boolean = get(TERMS, false)

    private fun <T> put(key: String, value: T) {
        val editor = preferenceStorage.edit()
        when (value) {
            is Float -> editor.putFloat(key, value)
            is Int -> editor.putInt(key, value)
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Long -> editor.putLong(key, value)
            else -> throw IOException()
        }
        editor.apply()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> get(key: String, default: T): T {
        val preferences = preferenceStorage
        return when (default) {
            is Float -> preferences.getFloat(key, default) as T
            is Int -> preferences.getInt(key, default) as T
            is String -> preferences.getString(key, default) as T
            is Boolean -> preferences.getBoolean(key, default) as T
            is Long -> preferences.getLong(key, default) as T
            else -> throw IOException()
        }
    }

    companion object {
        private const val TERMS = "PREFS_TERMS"
        private const val FILE_NAME = "shared_prefs"
        private const val PREFS_TOKEN = "PREFS_TOKEN"
        private const val PREFS_TOKEN_REFRESH = "PREFS_TOKEN_REFRESH"
        private const val PREFS_TOKEN_END = "PREFS_TOKEN_END"
    }
}
