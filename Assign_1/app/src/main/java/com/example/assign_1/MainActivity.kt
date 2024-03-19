package com.example.assign_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var textViewStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the button and text view
        trueButton = findViewById(R.id.truebutton)
        textViewStatus = findViewById(R.id.text_view_status)

        // Set up a click listener for the button to show a Toast message
        trueButton.setOnClickListener {
            Toast.makeText(
                this,
                getString(R.string.hello_world),
                Toast.LENGTH_SHORT
            ).show()
            textViewStatus.text = getString(R.string.hello_world)
        }
    }
}
