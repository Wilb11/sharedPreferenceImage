package com.example.simplecalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber1: EditText
    private lateinit var editTextNumber2: EditText
    private lateinit var spinnerOperations: Spinner
    private lateinit var buttonCalculate: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNumber1 = findViewById(R.id.editTextNumber1)
        editTextNumber2 = findViewById(R.id.editTextNumber2)
        spinnerOperations = findViewById(R.id.spinnerOperations)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textViewResult = findViewById(R.id.textViewResult)

        ArrayAdapter.createFromResource(
            this,
            R.array.arithmetic_operations_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerOperations.adapter = adapter
        }

        buttonCalculate.setOnClickListener {
            performCalculation()
        }
    }

    private fun performCalculation() {
        val number1 = editTextNumber1.text.toString().toDoubleOrNull()
        val number2 = editTextNumber2.text.toString().toDoubleOrNull()

        if (number1 == null || number2 == null) {
            textViewResult.text = getString(R.string.invalid_operand)
            return
        }

        val operation = spinnerOperations.selectedItem.toString()
        val result = when (operation) {
            "Addition" -> number1 + number2
            "Subtraction" -> number1 - number2
            "Multiplication" -> number1 * number2
            "Division" -> if (number2 != 0.0) number1 / number2 else null
            "Modulus" -> if (number2 != 0.0) number1 % number2 else null
            else -> null
        }

        if (result == null) {
            textViewResult.text = if (operation == "Division" || operation == "Modulus") {
                getString(R.string.divide_by_zero)
            } else {
                getString(R.string.error)
            }
        } else {
            textViewResult.text = getString(R.string.result_template, result.toString())
        }
    }
}