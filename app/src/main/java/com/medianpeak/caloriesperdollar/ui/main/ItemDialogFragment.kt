package com.medianpeak.caloriesperdollar.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment

import com.medianpeak.caloriesperdollar.R
import com.medianpeak.caloriesperdollar.ui.main.saved.SavedContent

private const val ARG_PARAM1 = "itemNumParam2"

class ItemDialogFragment(itemNumParam: Int) : DialogFragment() {
    private var itemNum: Int? = itemNumParam

    private lateinit var nameOfItem: TextView
    private lateinit var caloriesPerDollar: TextView
    private lateinit var caloriesPerServing: TextView
    private lateinit var numberOfServings: TextView
    private lateinit var priceOfItem: TextView
    private lateinit var deleteButton: Button

    private lateinit var itemDialogLayout: ConstraintLayout
    private lateinit var mListener: OnItemDialogFragmentInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemNum = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.fragment_item_dialog,
                view?.findViewById(android.R.id.content),false)

            nameOfItem = view.findViewById(R.id.name_of_item_dialog)
            caloriesPerDollar = view.findViewById(R.id.calories_per_dollar_dialog)
            caloriesPerServing = view.findViewById(R.id.calories_per_serving_dialog)
            numberOfServings = view.findViewById(R.id.number_of_servings_dialog)
            priceOfItem = view.findViewById(R.id.price_of_item_dialog)
            deleteButton = view.findViewById(R.id.delete_button)

            val item: SavedContent.SavedItem = SavedContent.ITEMS[itemNum!!]
            val nameOfItemText: String = item.nameOfItem
            val caloriesPerDollarText: String = "Calories Per Dollar: " + item.caloriesPerDollar
            val caloriesPerServingText: String = "Calories Per Serving: " + item.caloriesPerServing
            val numberOfServingsText: String = "Number Of Servings: " + item.numberOfServings
            val priceOfItemText: String = "Price Of Item: " + item.priceOfItem

            nameOfItem.text = nameOfItemText
            caloriesPerDollar.text = caloriesPerDollarText
            caloriesPerServing.text = caloriesPerServingText
            numberOfServings.text = numberOfServingsText
            priceOfItem.text = priceOfItemText

            itemDialogLayout = view.findViewById(R.id.item_dialog_layout)
            if (context is OnItemDialogFragmentInteractionListener) {
                mListener = context as OnItemDialogFragmentInteractionListener
            } else {
                throw RuntimeException("$context must implement OnItemDialogFragmentInteractionListener")
            }

            deleteButton.setOnClickListener {
                deleteItem(itemNum!!)
                mListener.onItemDialogFragmentInteraction()
                dialog?.cancel()
            }

            builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.close) { _, _ -> }
                //.setNegativeButton(R.string.delete) { _, _ ->
                //        dialog?.cancel()
                //}
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_dialog, container, false)
    }

    private fun deleteItem(itemNum: Int) {
        SavedContent.removeItemAt(itemNum)
    }

    interface OnItemDialogFragmentInteractionListener {
        fun onItemDialogFragmentInteraction()
    }

    companion object {
        @JvmStatic
        fun newInstance(itemNumParam2: Int) =
            ItemDialogFragment(itemNumParam2).apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, itemNumParam2)
                }
            }
    }
}
