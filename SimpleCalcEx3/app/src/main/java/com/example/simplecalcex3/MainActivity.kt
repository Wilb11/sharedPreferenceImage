package com.example.simplecalcex3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private var currentInput: StringBuilder = StringBuilder()
    private var currentOperator: String? = null
    private var operand1: Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)

        val numberButtonClickListener = View.OnClickListener {
            val button = it as Button
            appendToInput(button.text.toString())
        }

        // set click listener for number buttons (0-9)
        findViewById<Button>(R.id.button_one).setOnClickListener(numberButtonClickListener)
        findViewById<Button>(R.id.button_two).setOnClickListener(numberButtonClickListener)
        findViewById<Button>(R.id.button_three).setOnClickListener(numberButtonClickListener)
        findViewById<Button>(R.id.button_four).setOnClickListener(numberButtonClickListener)
        findViewById<Button>(R.id.button_five).setOnClickListener(numberButtonClickListener)
        findViewById<Button>(R.id.button_six).setOnClickListener(numberButtonClickListener)
        findViewById<Button>(R.id.button_seven).setOnClickListener(numberButtonClickListener)
        findViewById<Button>(R.id.button_eight).setOnClickListener(numberButtonClickListener)
        findViewById<Button>(R.id.button_nine).setOnClickListener(numberButtonClickListener)
        findViewById<Button>(R.id.button_zero).setOnClickListener(numberButtonClickListener)

        val operatorButtonClickListener = View.OnClickListener {
            val button = it as Button
            operator(button.text.toString())
        }

        findViewById<Button>(R.id.button_add).setOnClickListener(operatorButtonClickListener)
        findViewById<Button>(R.id.button_subtract).setOnClickListener(operatorButtonClickListener)
        findViewById<Button>(R.id.button_multiply).setOnClickListener(operatorButtonClickListener)
        findViewById<Button>(R.id.button_divide).setOnClickListener(operatorButtonClickListener)
        findViewById<Button>(R.id.button_sqrt).setOnClickListener {
            val input = currentInput.toString().toDoubleOrNull()
            if (input != null) {
                val result = sqrt(input)
                editText.setText(result.toString())
                currentInput.clear()
                operand1 = null
                currentOperator = null
            }
        }
        findViewById<Button>(R.id.button_dot).setOnClickListener {
            appendToInput(".")
        }
        findViewById<Button>(R.id.button_equal).setOnClickListener {
            calculate()
        }
    }

    private fun appendToInput(value: String) {
        currentInput.append(value)
        editText.setText(currentInput.toString())
    }

    private fun operator(operator: String) {
        if (operand1 == null) {
            operand1 = currentInput.toString().toDoubleOrNull()
            currentInput.clear()
            currentOperator = operator
        } else {
            calculate()
            currentOperator = operator
        }
    }

    private fun calculate() {
        val operand = currentInput.toString().toDoubleOrNull()
        if (operand1 != null && operand != null && currentOperator != null) {
            if (currentOperator == "/" && operand == 0.0) {
                // handle division by zero
                editText.setText("Divide by 0 Error")
                currentInput.clear()
                operand1 = null
                currentOperator = null
            } else {
                val result = if (currentOperator == "+") {
                    operand1!! + operand
                } else if (currentOperator == "-") {
                    operand1!! - operand
                } else if (currentOperator == "*") {
                    operand1!! * operand
                } else if (currentOperator == "/") {
                    operand1!! / operand
                } else {
                    throw IllegalArgumentException("Invalid Operator")
                }
                editText.setText(result.toString())
                currentInput.clear()
                operand1 = result
                currentOperator = null
            }
        }
    }
}