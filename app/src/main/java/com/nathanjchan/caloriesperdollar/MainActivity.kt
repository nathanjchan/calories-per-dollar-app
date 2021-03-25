package com.nathanjchan.caloriesperdollar

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.nathanjchan.caloriesperdollar.ui.main.CalculateFragment
import com.nathanjchan.caloriesperdollar.ui.main.ItemDialogFragment
import com.nathanjchan.caloriesperdollar.ui.main.ItemFragment
import com.nathanjchan.caloriesperdollar.ui.main.SectionsPagerAdapter
import com.nathanjchan.caloriesperdollar.ui.main.saved.SavedContent

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

        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // closes keyboard
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(viewPager.windowToken, 0)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
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