package examples.sg.com.languagetranslator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_SPEECH_RECOGNIZER = 1000
        const val LOG_TAG = "MainActivity"
    }

    private lateinit var recorderText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //CLick to start recording - look up at RecognizerIntent.
        btnRecord.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
            }
            startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER)
        }
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            btnRecord.isEnabled = true
        } else {
            Log.i(LOG_TAG, "Speech Recognition is not available")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SPEECH_RECOGNIZER) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    recorderText = result[0]
                    etOriginalText.setText(recorderText)
                }
            }
        }
    }
}
