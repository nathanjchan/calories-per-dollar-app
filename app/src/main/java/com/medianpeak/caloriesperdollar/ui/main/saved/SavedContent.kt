package com.medianpeak.caloriesperdollar.ui.main.saved

import java.util.ArrayList
import java.util.HashSet

object SavedContent {
    val ITEMS: MutableList<SavedItem> = ArrayList()
    //private const val COUNT = 25

    init {
        addItem(SavedItem("Mac n Cheese", 1.0, 2.0, 3.0, 4.0))
        addItem(SavedItem("Trader Joe's Ground Beef", 5.0, 6.0, 7.0, 8.0))
    }

    fun addItem(new_item: SavedItem) {
        ITEMS.add(new_item)
    }

    fun removeItemAt(itemIndex: Int) {
        if (itemIndex < getSize()) {
            ITEMS.removeAt(itemIndex)
        }
    }

    private fun getSize(): Int {
        return ITEMS.size
    }

    data class SavedItem(
        val nameOfItem: String,
        val caloriesPerDollar: Double,
        val caloriesPerServing: Double,
        val numberOfServings: Double,
        val priceOfItem: Double) {
    }
}