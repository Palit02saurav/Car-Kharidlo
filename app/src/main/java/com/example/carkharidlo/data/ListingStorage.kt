package com.example.carkharidlo.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ListingStorage {

    private const val PREFS_NAME = "ListingStorage"
    private const val KEY_LIST = "userListings"

    fun saveListing(context: Context, item: ListingItem) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        val existingJson = prefs.getString(KEY_LIST, "[]")
        val type = object : TypeToken<MutableList<ListingItem>>() {}.type
        val list: MutableList<ListingItem> = gson.fromJson(existingJson, type)

        list.add(item)

        prefs.edit().putString(KEY_LIST, gson.toJson(list)).apply()
    }

    fun getListings(context: Context): MutableList<ListingItem> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        val json = prefs.getString(KEY_LIST, "[]")
        val type = object : TypeToken<MutableList<ListingItem>>() {}.type

        return gson.fromJson(json, type)
    }
}
