package com.example.studentcontactapp.utils

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    companion object {
        private const val DARK_MODE = "darkMode"
        private const val FONT_SCALE = "fontScale"
        private const val NOTIFICATION_ENABLED = "notificationEnabled"
    }

    fun setDarkMode(enabled: Boolean) {
        editor.putBoolean(DARK_MODE, enabled)
        editor.apply()
    }

    fun isDarkMode(): Boolean {
        return pref.getBoolean(DARK_MODE, false)
    }

    fun setFontScale(scale: Float) {
        editor.putFloat(FONT_SCALE, scale)
        editor.apply()
    }

    fun getFontScale(): Float {
        return pref.getFloat(FONT_SCALE, 1.0f)
    }

    fun setNotificationEnabled(enabled: Boolean) {
        editor.putBoolean(NOTIFICATION_ENABLED, enabled)
        editor.apply()
    }

    fun isNotificationEnabled(): Boolean {
        return pref.getBoolean(NOTIFICATION_ENABLED, true)
    }
}
