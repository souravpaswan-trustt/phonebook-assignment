package com.example.phonebook_assignment

import android.content.Context
import android.content.SharedPreferences


class SharedPreferencesManager private constructor(context: Context, userId: String) {

    private val sharedPreferences: SharedPreferences

    init {
        val sharedPrefFileName = "$userId"
        sharedPreferences = context.getSharedPreferences(sharedPrefFileName, Context.MODE_PRIVATE)
    }

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "your_app_shared_pref"


        fun getInstance(context: Context, userId: String): SharedPreferencesManager {
            synchronized(this){ return SharedPreferencesManager(context, userId) }
        }
    }
}