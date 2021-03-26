package com.nathanjchan.caloriesperdollar.ui.main.saved

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.app.Application
import android.content.Context
import java.util.*

object SavedContent {
    private var ITEMS: MutableList<SavedItem> = ArrayList()
    //private const val COUNT = 25

    init {
//        addItem(SavedItem("Ritz Crackers Costco", 980.88, 80.0, 109.0, 8.89))
//        addItem(SavedItem("Cheez-its Costco", 855.51, 150.0, 45.0, 7.89))
    }

    fun addItem(new_item: SavedItem, context: Context) {
        ITEMS.add(new_item)
        saveData(context)
    }

    private fun saveData(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("shared preferences", MODE_PRIVATE) ?: return
        val gson = Gson()
        val json: String = gson.toJson(ITEMS)
        with(sharedPreferences.edit()) {
            putString("item list", json)
            apply()
        }
    }

    fun loadData(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE) ?: return
        val gson = Gson()
        val json: String = sharedPreferences.getString("item list", null) ?: return
        val type = object : TypeToken<MutableList<SavedItem>>() {}.type
        ITEMS = gson.fromJson(json, type)
    }

    fun removeItemAt(itemIndex: Int, context: Context) {
        if (itemIndex < getSize()) {
            ITEMS.removeAt(itemIndex)
            saveData(context)
        }
    }

    fun getItems(): MutableList<SavedItem> {
        return ITEMS
    }

    private fun getSize(): Int {
        return ITEMS.size
    }

    data class SavedItem(
        val nameOfItem: String,
        val caloriesPerDollar: Double,
        val caloriesPerServing: Double,
        val numberOfServings: Double,
        val priceOfItem: Double
    )
}