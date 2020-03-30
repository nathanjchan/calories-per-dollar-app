package com.medianpeak.caloriesperdollar.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.medianpeak.caloriesperdollar.R
import com.medianpeak.caloriesperdollar.ui.main.ItemFragment.OnListFragmentInteractionListener
import com.medianpeak.caloriesperdollar.ui.main.saved.SavedContent
import kotlinx.android.synthetic.main.fragment_item.view.*

class MyItemRecyclerViewAdapter(
    private val mValues: List<SavedContent.SavedItem>,
    private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as SavedContent.SavedItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mNameOfItemView.text = item.nameOfItem
        holder.mCaloriesPerDollarView.text = item.caloriesPerDollar.toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameOfItemView: TextView = mView.name_of_item
        val mCaloriesPerDollarView: TextView = mView.calories_per_dollar
    }
}
