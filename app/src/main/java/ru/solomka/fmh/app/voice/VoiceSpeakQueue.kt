package ru.solomka.fmh.app.voice


interface VoiceSpeakQueue {
    fun queueSpeak(textToSpeak: String): VoiceSpeakQueue
}