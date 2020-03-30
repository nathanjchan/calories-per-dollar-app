package com.medianpeak.caloriesperdollar.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.medianpeak.caloriesperdollar.R
import com.medianpeak.caloriesperdollar.ui.main.saved.SavedContent
import kotlin.math.round
import kotlin.properties.Delegates


class CalculateFragment : Fragment() {

    // EditText
    private lateinit var caloriesPerServing: EditText
    private lateinit var numberOfServings: EditText
    private lateinit var priceOfItem: EditText
    private lateinit var nameOfItem: EditText

    // TextView
    private lateinit var caloriesPerDollar: TextView
    private lateinit var saveWarning: TextView

    // Buttons
    private lateinit var clearButton: Button
    private lateinit var saveButton: Button

    // Doubles
    private var caloriesPerServingNum by Delegates.notNull<Double>()
    private var numberOfServingsNum by Delegates.notNull<Double>()
    private var priceOfItemNum by Delegates.notNull<Double>()
    private var caloriesPerDollarNum by Delegates.notNull<Double>()

    private lateinit var calculateLayout: ConstraintLayout
    private lateinit var mListener: OnCalculateFragmentInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_calculate, container, false)

        // EditText
        caloriesPerServing = view.findViewById(R.id.calories_per_serving_input)
        numberOfServings = view.findViewById(R.id.number_of_servings_input)
        priceOfItem = view.findViewById(R.id.price_of_item_input)
        nameOfItem = view.findViewById(R.id.name_of_item_input)

        // TextView
        caloriesPerDollar = view.findViewById(R.id.calories_per_dollar_output)
        saveWarning = view.findViewById(R.id.save_warning)

        // Button
        clearButton = view.findViewById(R.id.clear_button)
        saveButton = view.findViewById(R.id.save_button)

        // Save Button
        calculateLayout = view.findViewById(R.id.calculate_layout)
        if (context is OnCalculateFragmentInteractionListener) {
            mListener = context as OnCalculateFragmentInteractionListener
        } else {
            throw RuntimeException("$context must implement OnCalculateFragmentInteractionListener")
        }

        createTextChangedListener(caloriesPerServing)
        createTextChangedListener(numberOfServings)
        createTextChangedListener(priceOfItem)
        createTextChangedListener(nameOfItem)

        clearButton.setOnClickListener {
            clearFields()
        }
        saveButton.setOnClickListener {
            saveItem()
            // tells the MainActivity that the time has come
            // mListener.onCalculateFragmentInteraction()
            // (moved to save function)
            // closes keyboard
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(calculateLayout.windowToken, 0)
        }

        return view
    }

    private fun createTextChangedListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateCaloriesPerDollar()
            }
        })
    }

    private fun createNumbers() {
        caloriesPerServingNum = caloriesPerServing.text.toString().toDouble()
        numberOfServingsNum = numberOfServings.text.toString().toDouble()
        priceOfItemNum = priceOfItem.text.toString().toDouble()
    }

    private fun calculateCaloriesPerDollar() {
        caloriesPerDollarNum = (round(((caloriesPerServingNum * numberOfServingsNum) / priceOfItemNum) * 100) / 100)
    }

    private fun updateCaloriesPerDollar() {
        resetWarning()
        try {
            createNumbers()
            calculateCaloriesPerDollar()
            caloriesPerDollar.text = caloriesPerDollarNum.toString()
        } catch(e: NumberFormatException) {
            caloriesPerDollar.text = resources.getString(R.string.cal)
        }
    }

    private fun saveItem() {
        if (caloriesPerDollar.text != resources.getString(R.string.cal)) {
            if (!nameOfItem.text.isBlank()) {
                val newItem = SavedContent.SavedItem(nameOfItem.text.toString(), caloriesPerDollarNum, caloriesPerServingNum, numberOfServingsNum, priceOfItemNum)
                if (newItem !in SavedContent.ITEMS) {
                    SavedContent.addItem(newItem)
                    clearFields()
                    // tell the MainActivity that the time has come
                    mListener.onCalculateFragmentInteraction()
                    saveWarning.text = resources.getString(R.string.saved_exclamation)
                } else {
                    saveWarning.text = resources.getString(R.string.item_already_saved)
                }
            } else {
                saveWarning.text = resources.getString(R.string.need_name_of_item_to_save)
            }
        } else {
            saveWarning.text = resources.getString(R.string.need_calories_per_dollar_to_save)
        }
    }

    private fun resetWarning() {
        saveWarning.text = ""
    }

    private fun clearFields() {
        caloriesPerServing.text.clear()
        numberOfServings.text.clear()
        priceOfItem.text.clear()
        nameOfItem.text.clear()
    }

    interface OnCalculateFragmentInteractionListener {
        fun onCalculateFragmentInteraction()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CalculateFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
