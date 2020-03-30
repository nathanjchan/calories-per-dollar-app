package com.medianpeak.caloriesperdollar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.medianpeak.caloriesperdollar.ui.main.*
import com.medianpeak.caloriesperdollar.ui.main.saved.SavedContent

class MainActivity : AppCompatActivity(),
    ItemFragment.OnListFragmentInteractionListener,
    CalculateFragment.OnCalculateFragmentInteractionListener,
    ItemDialogFragment.OnItemDialogFragmentInteractionListener {

    private lateinit var viewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    override fun onListFragmentInteraction(item: SavedContent.SavedItem) {
        val newFragment = ItemDialogFragment(SavedContent.ITEMS.indexOf(item))
        newFragment.show(supportFragmentManager, "item")
    }

    override fun onCalculateFragmentInteraction() {
        // when save button pressed, update the view_pager
        viewPager.adapter?.notifyDataSetChanged()
    }

    override fun onItemDialogFragmentInteraction() {
        // when delete button pressed
        viewPager.adapter?.notifyDataSetChanged()
    }
}