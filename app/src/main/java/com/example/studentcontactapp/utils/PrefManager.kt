package com.example.studentcontactapp.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences("StudentContactPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    companion object {
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val USERNAME = "username"
        private const val REMEMBER_ME = "rememberMe"
    }

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGGED_IN, false)
    }

    fun setUsername(username: String) {
        editor.putString(USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String? {
        return pref.getString(USERNAME, "")
    }

    fun setRememberMe(rememberMe: Boolean) {
        editor.putBoolean(REMEMBER_ME, rememberMe)
        editor.apply()
    }

    fun isRememberMe(): Boolean {
        return pref.getBoolean(REMEMBER_ME, false)
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}
