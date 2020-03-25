package com.medianpeak.caloriesperdollar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.medianpeak.caloriesperdollar.ui.main.CalculateFragment
import com.medianpeak.caloriesperdollar.ui.main.ItemFragment
import com.medianpeak.caloriesperdollar.ui.main.MyItemRecyclerViewAdapter
import com.medianpeak.caloriesperdollar.ui.main.SectionsPagerAdapter
import com.medianpeak.caloriesperdollar.ui.main.saved.SavedContent
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), ItemFragment.OnListFragmentInteractionListener, CalculateFragment.OnCalculateFragmentInteractionListener {

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
    }

    override fun onCalculateFragmentInteraction() {
        // when save button pressed, update the view_pager
        viewPager.adapter?.notifyDataSetChanged()
    }
}