package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.lang.NumberFormatException


class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private lateinit var listener: OnButtonSubmitListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnButtonSubmitListener

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            try {
                val min = view.findViewById<EditText>(R.id.min_value).text.toString().toInt()
                val max = view.findViewById<EditText>(R.id.max_value).text.toString().toInt()
                when {
                    min < 0 || max < 0 -> showToast("Only positive numbers are allowed")
                    min > max -> showToast("Min should be lower than max number")
                    else -> listener.onButtonSubmit(min, max)
                }
            } catch (error: NumberFormatException) {
                showToast("Please, fill in fields with valid numbers")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            activity, message,
            Toast.LENGTH_LONG
        ).show()
    }

    interface OnButtonSubmitListener {
        fun onButtonSubmit(min: Int, max: Int)
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}