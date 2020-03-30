package com.medianpeak.caloriesperdollar.ui.main.saved

import java.util.ArrayList
import java.util.HashSet

object SavedContent {
    val ITEMS: MutableList<SavedItem> = ArrayList()
    //private const val COUNT = 25

    init {
        addItem(SavedItem("Ritz Crackers Costco", 980.88, 80.0, 109.0, 8.89))
        addItem(SavedItem("Cheez-its Costco", 855.51, 150.0, 45.0, 7.89))
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