package com.example.testallthings.utils.prefUtils

import android.annotation.SuppressLint
import android.content.Context
import com.example.testallthings.di.MyApplication
import dagger.hilt.android.qualifiers.ApplicationContext


class PrefUtils {

    companion object{
        fun saveToPref(context: Context, key:String, value:Any){
            var myPrefs= androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            when(value){
                is Int -> myPrefs.edit().putInt(key,value).apply()
                is String -> myPrefs.edit().putString(key,value).apply()
                is Float -> myPrefs.edit().putFloat(key,value).apply()
                is Boolean -> myPrefs.edit().putBoolean(key,value).apply()
                is Long -> myPrefs.edit().putLong(key,value).apply()
            }
        }

        fun getFromPref(context: Context,key: String,defaultValue:Any):Any?{
            var myPrefs= androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return when(defaultValue){
                is Int -> myPrefs.getInt(key,defaultValue)
                is String -> myPrefs.getString(key,defaultValue)
                is Float -> myPrefs.getFloat(key,defaultValue)
                is Boolean -> myPrefs.getBoolean(key,defaultValue)
                is Long -> myPrefs.getLong(key,defaultValue)
                else -> null
            }
        }
    }

}