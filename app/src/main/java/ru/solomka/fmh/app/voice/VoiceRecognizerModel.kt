package ru.solomka.fmh.app.voice

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.ArrayDeque
import java.util.Locale

class VoiceRecognizerModel(
    private val context: Context
) : TextToSpeech.OnInitListener, VoiceSpeakQueue {

    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false

    private val pendingSpeechQueue = ArrayDeque<String>()

    init {
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("ru", "RU"))

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                requestTTSDataInstall()
            } else {
                isInitialized = true
                while (pendingSpeechQueue.isNotEmpty()) {
                    val text = pendingSpeechQueue.poll() ?: break
                    speakNow(synthesisCodeReplace(text))
                    Log.e("TTS", text)
                }
            }
        } else {
            Log.e("TTS", "Инициализация не удалась: $status")
        }
    }

    fun speakNow(text: String) {
        textToSpeech?.speak(
            text,
            TextToSpeech.QUEUE_ADD,
            null,
            null
        )
    }

    private fun requestTTSDataInstall() {
        val installIntent = Intent().apply {
            action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(installIntent)
    }

    override fun queueSpeak(textToSpeak: String): VoiceSpeakQueue {
        if (textToSpeak.isBlank()) return this

        val normalizedWork = synthesisCodeReplace(textToSpeak)
        if(isInitialized)
            speakNow(normalizedWork)
        else
            pendingSpeechQueue.add(normalizedWork)

        return this
    }

    fun stop() {
        textToSpeech?.stop()
    }

    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }

    fun isSpeaking(): Boolean = textToSpeech?.isSpeaking ?: false

    private fun synthesisCodeReplace(word: String): String {
        return word.replace("'", "\u0301")
    }

}