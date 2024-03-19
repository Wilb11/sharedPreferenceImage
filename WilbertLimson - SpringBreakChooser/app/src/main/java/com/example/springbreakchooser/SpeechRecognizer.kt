import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast

class SpeechRecognitionHelper(context: Context, private val onResult: (String) -> Unit) {
    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private var listening: Boolean = false

    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            // Ready to start listening
        }

        override fun onBeginningOfSpeech() {
            // User has started speaking
        }

        override fun onRmsChanged(rmsdB: Float) {
            // Sound level in the audio stream has changed
        }

        override fun onBufferReceived(buffer: ByteArray?) {
            // More sound has been received
        }

        override fun onEndOfSpeech() {
            // User has stopped speaking
        }

        override fun onError(error: Int) {
            // An error occurred
            Toast.makeText(context, "Speech recognition error: $error", Toast.LENGTH_SHORT).show()
            listening = false
        }

        override fun onResults(results: Bundle?) {
            // We have results
            results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.let { matches ->
                if (matches.isNotEmpty()) {
                    // Return the first match
                    onResult(matches[0])
                }
            }
            listening = false
        }

        override fun onPartialResults(partialResults: Bundle?) {
            // We have partial results
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
            // An event occurred
        }
    }

    init {
        speechRecognizer.setRecognitionListener(recognitionListener)
    }

    fun startListening(intent: Intent) {
        if (!listening) {
            speechRecognizer.startListening(intent)
            listening = true
        }
    }

    fun stopListening() {
        if (listening) {
            speechRecognizer.stopListening()
            listening = false
        }
    }

    fun destroy() {
        speechRecognizer.destroy()
    }
}
