package com.example.snackbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener {
            Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.correct_snackbar),
                Snackbar.LENGTH_SHORT
            ).show()
            falseButton.setOnClickListener {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.incorrect_snackbar),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }
}

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        trueButton = findViewById(R.id.true_button)
//        falseButton = findViewById(R.id.false_button)
//        trueButton.setOnClickListener { view: View ->
//        // Do something in response to the click here
//            Toast.makeText(
//                this,
//                R.string.correct_toast,
//                Toast.LENGTH_SHORT
//            ).show()
//
//
//        }
//        falseButton.setOnClickListener { view: View ->
//        // Do something in response to the click here
//            Toast.makeText(
//                this,
//                R.string.incorrect_toast,
//                Toast.LENGTH_SHORT
//            ).show()
//        }