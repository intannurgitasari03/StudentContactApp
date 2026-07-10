package com.example.studentcontactapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.studentcontactapp.utils.SettingsManager

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val settingsManager = SettingsManager(this)
        if (settingsManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context) {
        val settingsManager = SettingsManager(newBase)
        val config = Configuration(newBase.resources.configuration)
        config.fontScale = settingsManager.getFontScale()
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
}
