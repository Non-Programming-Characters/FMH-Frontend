package ru.solomka.fmh.app.voice


interface VoiceSpeakQueue {
    fun queue(textToSpeak: String): VoiceSpeakQueue

    fun start()

    fun stop()
}