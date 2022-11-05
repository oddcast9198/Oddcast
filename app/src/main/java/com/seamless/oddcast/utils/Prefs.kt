package com.seamless.oddcast.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs constructor(context: Context) {

    internal var typeOfObject = object : TypeToken<Any>() {

    }.type

    val all: Map<String, *>
        get() = preferences.all

    init {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun save(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun save(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun save(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun save(key: String, value: Float) {
        editor.putFloat(key, value).apply()

    }

    fun save(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    fun save(key: String, value: Set<String>) {
        editor.putStringSet(key, value).apply()
    }

    //    // to save object in prefrence
    fun save(key: String?, `object`: Any?) {
        if (`object` == null) {
            throw IllegalArgumentException("object is null")
        }

        if (key == "" || key == null) {
            throw IllegalArgumentException("key is empty or null")
        }

        editor.putString(key, GSON.toJson(`object`.toString())).apply()
    }

    // To get object from prefrences

    fun <T> getObject(key: String, a: Class<T>): T? {

        val gson = preferences.getString(key, null)
        return if (gson == null) {
            null
        } else {
            try {
                GSON.fromJson(gson, a)
            } catch (e: Exception) {
                throw IllegalArgumentException("Object storage with key "
                        + key + " is instanceof ic_other_normal class")
            }

        }
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return preferences.getBoolean(key, defValue)
    }

    fun getString(key: String, defValue: String?): String? {
        return preferences.getString(key, defValue)
    }

    fun getInt(key: String, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    fun getFloat(key: String, defValue: Float): Float {
        return preferences.getFloat(key, defValue)
    }

    fun getLong(key: String, defValue: Long): Long {
        return preferences.getLong(key, defValue)
    }

    fun getStringSet(key: String, defValue: Set<String>): Set<String>? {
        return preferences.getStringSet(key, defValue)
    }

    fun remove(key: String) {
        editor.remove(key).apply()
    }

    fun removeAll() {
        editor.clear()
        editor.apply()
    }

    operator fun contains(key: String): Boolean {
        return if (preferences.contains(key)) {
            true
        } else false
    }

    private class Builder(context: Context?) {

        private val context: Context

        init {
            if (context == null) {
                throw IllegalArgumentException("Context must not be null.")
            }
            this.context = context.applicationContext
        }

        /**
         * Method that creates an instance of Prefs
         *
         * @return an instance of Prefs
         */
        fun build(): Prefs {
            return Prefs(context)
        }
    }

    companion object {

        private val TAG = "Prefs"

        internal var singleton: Prefs? = null

        internal lateinit var preferences: SharedPreferences

        internal lateinit var editor: SharedPreferences.Editor

        private val GSON = Gson()

        fun with(context: Context): Prefs {
            if (singleton == null) {
                singleton = Builder(context).build()
            }
            return singleton as Prefs
        }
    }
}