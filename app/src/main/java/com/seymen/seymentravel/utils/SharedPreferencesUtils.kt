package com.seymen.seymentravel.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtils {

    private var sharedPref: SharedPreferences? = null

    constructor(activity: Activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
    }


    fun readDataString(key: String, default: String): String {
        sharedPref?.let {
            return it.getString(key, default).toString()
        }
        return default
    }

    fun writeDataString(key: String, value: String): Boolean {
        sharedPref?.let {
            with(it.edit()) {
                putString(key, value)
                apply()
                return commit()
            }
        }
        return false
    }


}