package com.example.temperatureconverter

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private lateinit var celsiusSeekBar: SeekBar
    private lateinit var fahrenheitSeekBar: SeekBar
    private lateinit var interestingMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        celsiusSeekBar = findViewById(R.id.celsiusSeekBar)
        fahrenheitSeekBar = findViewById(R.id.fahrenheitSeekBar)
        interestingMessage = findViewById(R.id.interestingMessage)

        celsiusSeekBar.max = 100 // Celsius range
        fahrenheitSeekBar.max = 212 // Fahrenheit range

        celsiusSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    updateFahrenheit(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        fahrenheitSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    if (progress < 32) {
                        fahrenheitSeekBar.progress = 32
                        celsiusSeekBar.progress = 0
                    } else {
                        updateCelsius(progress)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun updateFahrenheit(celsius: Int) {
        val fahrenheit = celsius * 9/5 + 32
        fahrenheitSeekBar.progress = fahrenheit
        updateMessage(celsius)
    }

    private fun updateCelsius(fahrenheit: Int) {
        val celsius = (fahrenheit - 32) * 5/9
        celsiusSeekBar.progress = celsius
        updateMessage(celsius)
    }

    private fun updateMessage(celsius: Int) {
        interestingMessage.text = if (celsius <= 20) {
            "I wish it were warmer."
        } else {
            "I wish it were colder."
        }
    }
}
