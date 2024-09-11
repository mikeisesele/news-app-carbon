package com.michael.sharedPreference

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class SharedPref @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    /*Load details From Shared Preferences*/
    fun <T> loadFromSharedPref(prefType: PreferenceType, key: String): T {
        with(sharedPreferences) {
            return when (prefType) {
                PreferenceType.STRING -> getString(key, "") as T
                PreferenceType.INT -> getInt(key, 0) as T
                PreferenceType.BOOLEAN -> getBoolean(key, false) as T
                PreferenceType.FLOAT -> getFloat(key, 0f) as T
                PreferenceType.LONG -> getLong(key, 0) as T
            }
        }
    }

    /*Save details to Shared Preferences*/
    fun <T> saveToSharedPref(prefKey: String, prefValue: T) {
        with(editor) {
            apply {
                when (prefValue) {
                    is String -> putString(prefKey, prefValue)
                    is Boolean -> putBoolean(prefKey, prefValue)
                    is Int -> putInt(prefKey, prefValue)
                    is Float -> putFloat(prefKey, prefValue)
                    is Long -> putLong(prefKey, prefValue)
                }
            }
        }
    }

    /*Clear values in Shared Preferences*/
    fun clearSharedPref() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun removeSharedPref(key: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
}

enum class PreferenceType {
    STRING,
    BOOLEAN,
    INT,
    FLOAT,
    LONG,
}
