package com.example.DoStudy

import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    enum class ThemeMode { LIGHT, DARK }

    fun applyTheme(themeMode: ThemeMode) {
        when (themeMode) {
            ThemeMode.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemeMode.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}