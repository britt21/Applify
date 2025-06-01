package com.mobile.lauchly.cache

import android.content.Context


const val STEP = "step"
class DataManager(var context: Context) {

    fun saveData(key: String, defaultValue: String) {
        var pref = context.getSharedPreferences("appPref", Context.MODE_PRIVATE)
        pref.edit().putString(key, defaultValue).apply()
    }


    fun readData(key: String, defaultValue: String): String {
        var pref = context.getSharedPreferences("appPref", Context.MODE_PRIVATE)
        var value =  pref.getString(key, defaultValue)!!
        return value
    }


}