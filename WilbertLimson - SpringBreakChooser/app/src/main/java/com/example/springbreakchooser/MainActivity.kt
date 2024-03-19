package com.example.springbreakchooser
import ShakeDetector
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer
import android.speech.RecognizerIntent
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.net.Uri
import android.speech.SpeechRecognizer
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.*
import androidx.core.app.ActivityCompat.startActivityForResult
import org.json.JSONException
import org.json.JSONObject
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var listView: ListView
    private val REQUEST_CODE_SPEECH_INPUT = 100
    private var selectedLanguage = "English"
    private var selectedDestination = ""
    private lateinit var shakeDetector: ShakeDetector


//    // Define your language to location mapping
//    private val languageToLocationMap = mapOf(
//        "Spanish" to "geo:19.432608,-99.133209", // Mexico City, Mexico
//        "French" to "geo:48.856613,2.352222",   // Paris, France
//        "Chinese" to "geo:39.904200,116.407396", // Beijing, China
//        "Italian" to "geo:41.9028,12.4964",      // Rome, Italy
//        "Japanese" to "geo:35.689487,139.691706",// Tokyo, Japan
//        "Arabic" to "geo:24.774265,46.738586"    // Riyadh, Saudi Arabia
//    )
//
//    private val languageToHelloMap = mapOf(
//        "Spanish" to "¡Hola!",
//        "French" to "Bonjour",
//        "Chinese" to "你好",
//        "Italian" to "Ciao",
////        "Japanese" to "こんにちは", // Konnichiwa
////        "Arabic" to "مرحبا"         // Marhaba
//    )
    private val languageToLocationMap = mapOf(
        "Spanish" to "geo:19.432608,-99.133209", // Mexico City, Mexico
        "French" to "geo:48.856613,2.352222",   // Paris, France
        "Chinese" to "geo:39.904200,116.407396" // Beijing, China
    )

    private val languageToHelloMap = mapOf(
        "Spanish" to "¡Hola!",
        "French" to "Bonjour",
        "Japanese" to "こんにちは",
//        "Chinese" to "你好"
    )

    private val speechRecognizerIntent by lazy {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            // The language can be mapped from the languageToLocationMap based on the selection
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something in $selectedLanguage")
        }
    }

    private lateinit var speechRecognizer: SpeechRecognizer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        listView = findViewById(R.id.listView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, languageToLocationMap.keys.toList())
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedLanguage = adapter.getItem(position) ?: "English"
            promptSpeechInput()
        }

        // Shake detection and opening map logic should be implemented here...
        // Initialize ShakeDetector
        shakeDetector = ShakeDetector(this).apply {
            onShakeListener = {
                openMapToLocation()
            }
        }

        // ... other initialization code
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
    }

//    private fun promptSpeechInput() {
//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedLanguage)
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something in $selectedLanguage")
//
//        try {
//            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, getLanguageCode(selectedLanguage))
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something in $selectedLanguage")

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLanguageCode(language: String): String {
        // Return the language code based on the selected language
        // You will need to define the language codes for each supported language
        return when (language) {
            "Spanish" -> "es"
            "French" -> "fr"
            "Chinese" -> "zh"
            else -> Locale.getDefault().language // Fallback to default locale
        }
    }


    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && null != data) {
//            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//            editText.setText(result?.get(0) ?: "")
//        }
//        val spokenText = result?.get(0) ?: ""
//        translateText(spokenText, selectedLanguage)
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = result?.get(0) ?: ""
            translateText(spokenText, selectedLanguage) // Move this line inside the if block
            editText.setText(spokenText)
        }
    }

    private val client = OkHttpClient()

//    private fun translateText(spokenText: String, languageCode: String) {
//        // Make sure to call this on a background thread
//        CoroutineScope(Dispatchers.IO).launch {
//            val apiKey = "YOUR_API_KEY"
//            val url = "https://translation.googleapis.com/language/translate/v2?key=$apiKey"
//            val jsonMimeType = "application/json; charset=utf-8"
//            val postBody = """
//            {
//              "q": "$spokenText",
//              "target": "$languageCode"
//            }
//        """.trimIndent()
//
//            val request = Request.Builder()
//                .url(url)
//                .addHeader("Content-Type", jsonMimeType)
//                .addHeader("Authorization", "Bearer $apiKey")
//                .post(postBody.toRequestBody())
//                .build()
//
//            try {
//                val response = client.newCall(request).execute()
//                val responseBody = response.body?.string()
//
//                // Since this will be executed on a background thread, switch to the main thread to update the UI
//                withContext(Dispatchers.Main) {
//                    // TODO: Parse the JSON response to get the translated text and update the UI
//                    // This is a placeholder example and should be replaced with actual JSON parsing logic
//                    editText.setText(responseBody) // Replace with actual translated text
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                // Handle the error appropriately
//            }
//        }
//    }
    private fun translateText(spokenText: String, languageCode: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = "YOUR_ACTUAL_API_KEY" // Replace with your actual API key.
            val url = "https://translation.googleapis.com/language/translate/v2?key=$apiKey"
            val jsonMimeType = "application/json; charset=utf-8"
            val postBody = """
            {
              "q": "$spokenText",
              "target": "$languageCode"
            }
            """.trimIndent()

            val request = Request.Builder()
                .url(url)
                .addHeader("Content-Type", jsonMimeType)
                .post(postBody.toRequestBody())
                .build()

            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                withContext(Dispatchers.Main) {
                    val translatedText = parseTranslation(responseBody) // Implement this parsing function
                    editText.setText(translatedText)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the error appropriately
            }
        }
    }

    // The parsing function to extract the translated text from the JSON response
    private fun parseTranslation(responseBody: String?): String {
        return try {
            val jsonResponse = JSONObject(responseBody)
            val data = jsonResponse.getJSONObject("data")
            val translations = data.getJSONArray("translations")
            val firstTranslation = translations.getJSONObject(0)
            firstTranslation.getString("translatedText")
        } catch (e: JSONException) {
            e.printStackTrace()
            "Error parsing translation"
        }
    }


//    private fun openMapToLocation() {
//        selectedDestination = languageToLocationMap[selectedLanguage].orEmpty()
//        val gmmIntentUri = Uri.parse(selectedDestination)
//        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//        mapIntent.setPackage("com.google.android.apps.maps")
//        startActivity(mapIntent)
//
//        // You need to have a MediaPlayer ready with the greetings in different languages
//        playGreetingForLanguage(selectedLanguage)
//    }
    private fun openMapToLocation() {
        selectedDestination = languageToLocationMap[selectedLanguage].orEmpty()
        playGreetingForLanguage(selectedLanguage) // Play the greeting
        val gmmIntentUri = Uri.parse(selectedDestination)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        }
    }


    private fun playGreetingForLanguage(language: String) {
        val greeting = languageToHelloMap[language] ?: "Hello"
        val resourceId = when (language) {
            "Spanish" -> R.raw.hello_spanish
            "French" -> R.raw.hello_french
//            "Chinese" -> R.raw.hello_chinese
            "Japanese" -> R.raw.hello_japanese
            else -> 0 // You should handle this case appropriately
        }

        if (resourceId != 0) {
            val mediaPlayer = MediaPlayer.create(this, resourceId)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { mp -> mp.release() }
        }
    }


//    private fun playGreetingForLanguage(language: String) {
//        val greeting = languageToHelloMap[language] ?: "Hello"
//        // Use MediaPlayer to play the greeting sound
//        // You should have raw audio resources for each language's greeting
//        val resourceId = when (language) {
//            "Spanish" -> R.raw.hello_spanish
//            "French" -> R.raw.hello_french
////            "Chinese" -> R.raw.hello_chinese
////            "Italian" -> R.raw.hello_italian
//            "Japanese" -> R.raw.hello_japanese
////            "Arabic" -> R.raw.hello_arabic
//            else -> null
//        }
////        resourceId?.let {
////            val mediaPlayer = MediaPlayer.create(this, it)
////            mediaPlayer.setOnCompletionListener { mp -> mp.release() }
////            mediaPlayer.start()
////        }
//    }

    // You need to implement a sensor listener for shake detection
    // Check Android documentation on how to detect shake gestures

    override fun onResume() {
        super.onResume()
        shakeDetector.start()
    }

    override fun onPause() {
        super.onPause()
        shakeDetector.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy() // Don't forget to release the resources
    }

//    private fun promptSpeechInput() {
//        try {
//            speechRecognizer?.startListening(speechRecognizerIntent)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

}
