package ru.solomka.fmh.app.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.ArrayDeque
import java.util.Locale

class VoiceRecognizerModel(
    private val context: Context
) : TextToSpeech.OnInitListener, VoiceSpeakQueue {

    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false

    // ✅ Синхронизация доступа к очереди
    private val queueLock = Any()
    private val pendingSpeechQueue = ArrayDeque<String>()

    // ✅ Флаг: уже идёт обработка очереди
    private var isProcessing = false

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
            }
        }
    }

    override fun queue(textToSpeak: String): VoiceSpeakQueue {
        if (textToSpeak.isBlank()) return this

        // ✅ Синхронизированное добавление
        synchronized(queueLock) {
            pendingSpeechQueue.add(textToSpeak)
        }
        Log.d("VoiceQueue", "Добавлено: '$textToSpeak', в очереди: ${pendingSpeechQueue.size}")
        return this
    }

    override fun start() {
        // ✅ Гарантируем запуск в главном потоке
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Handler(Looper.getMainLooper()).post { startInternal() }
            return
        }
        startInternal()
    }

    private fun startInternal() {
        // ✅ Защита от рекурсивных/параллельных вызовов
        synchronized(queueLock) {
            if (isProcessing || pendingSpeechQueue.isEmpty()) return
            isProcessing = true
        }

        // ✅ Безопасное извлечение с синхронизацией
        while (true) {
            val text = synchronized(queueLock) {
                if (pendingSpeechQueue.isEmpty()) {
                    isProcessing = false
                    null
                } else {
                    pendingSpeechQueue.poll()  // ✅ Удаляет и возвращает первый
                }
            } ?: break  // ✅ Выход, если очередь пуста

            // ✅ Озвучиваем с обработкой завершения
            speakWithCallback(text)

            // ✅ ВАЖНО: не продолжаем цикл сразу!
            // TTS асинхронный, ждём onDone для следующего элемента
            return
        }
    }

    fun speakWithCallback(text: String) {
        val utteranceId = text.hashCode().toString()
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
        }

        // ✅ Устанавливаем слушатель для продолжения очереди
        textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.d("VoiceQueue", "Начало: $utteranceId")
            }

            override fun onDone(utteranceId: String?) {
                Log.d("VoiceQueue", "Завершено: $utteranceId")
                // ✅ После завершения — пробуем озвучить следующий
                startInternal()
            }

            override fun onError(utteranceId: String?) {
                Log.e("VoiceQueue", "Ошибка: $utteranceId")
                // ✅ При ошибке тоже продолжаем
                startInternal()
            }

            // API 26+
            override fun onError(utteranceId: String?, errorCode: Int) {
                Log.e("VoiceQueue", "Ошибка $errorCode: $utteranceId")
                startInternal()
            }
        })

        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, params, null)
    }

    override fun stop() {
        synchronized(queueLock) {
            pendingSpeechQueue.clear()
            isProcessing = false
        }
        textToSpeech?.stop()
    }

    private fun requestTTSDataInstall() {
        val installIntent = Intent().apply {
            action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(installIntent)
    }

    fun shutdown() {
        stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }

    fun isSpeaking(): Boolean = textToSpeech?.isSpeaking == true
}