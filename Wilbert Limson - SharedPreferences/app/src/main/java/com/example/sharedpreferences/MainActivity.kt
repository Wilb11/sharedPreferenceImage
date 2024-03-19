package com.example.sharedpreferences

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var imageView: ImageView
    private val images = arrayOf(R.drawable.apple_action_name, R.drawable.mango_action_name, R.drawable.strawberry_action_name, R.drawable.elephant_action_name) // Add your image IDs here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_text_input)
        imageView = findViewById(R.id.image_view)

        loadState()


        findViewById<Button>(R.id.button_change_image).setOnClickListener {
            val randomImage = images.random()
            imageView.setImageResource(randomImage)
            saveState()
        }
        whenWillSharedPreferencesFail()
    }

    private fun loadState() {
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        editText.setText(prefs.getString("text", ""))
        val imageName = prefs.getString("image", null)
        if (imageName != null) {
            val resId = resources.getIdentifier(imageName, "drawable", packageName)
            imageView.setImageResource(resId)
        }
    }

    private fun saveState() {
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString("text", editText.text.toString())
            val resName = resources.getResourceEntryName(images.last()) // Save the last shown image name
            putString("image", resName)
            apply()
        }
    }

    //Answering the remember part
    // This function simulates scenarios where SharedPreferences will fail to restore state
    fun whenWillSharedPreferencesFail() {
        // 1. If the user clears the app data manually from the system settings
        val messageClearData = "SharedPreferences will fail to restore state if the user clears the app data manually from the settings."

        // 2. If the user uninstalls the app and then reinstalls it
        val messageUninstall = "SharedPreferences will not restore state after the user uninstalls and reinstalls the app."

        // 3. If the operating system clears SharedPreferences
        // This can happen if the device is running low on storage space
        val messageOsClears = "SharedPreferences may be cleared by the OS if the device is running low on storage."

        // 4. If there is an error in the code when reading from SharedPreferences
        // For example, trying to read an integer when it is stored as a string
        val messageCodeError = "If there is a coding error, like a type mismatch while reading from SharedPreferences, it will fail to restore the state."

        // Display or log these messages for educational purposes
        Log.d("SharedPreferencesFail", messageClearData)
        Log.d("SharedPreferencesFail", messageUninstall)
        Log.d("SharedPreferencesFail", messageOsClears)
        Log.d("SharedPreferencesFail", messageCodeError)
    }

    override fun onDestroy() {
        super.onDestroy()
        saveState() // Ensure state is saved when the activity is destroyed
    }
}
