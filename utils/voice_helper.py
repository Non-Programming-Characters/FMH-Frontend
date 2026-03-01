import os
import tempfile
import time
import wave
from collections import deque

import numpy as np
import sounddevice as sd
import speech_recognition as sr
from plyer import tts as native_tts

from core.class_di import registry
from core.component_type import ComponentType
from core.container_di import component


@component
@registry.register(component_type=ComponentType.COMPONENT, name="VOICE_RECOGNIZER")
class VoiceRecognizer:
    def __init__(
            self,
            language='ru-RU',
            sample_rate=16000,

            speech_threshold=450,
            silence_threshold=400,

            silence_duration=1,
            chunk_duration=0.3,
            min_recording=0.1,
            vad_cooldown=0.2,
            max_recording=60,

            pre_roll_duration=0.6,
    ):
        self.recognizer = sr.Recognizer()
        self.language = language
        self.sample_rate = sample_rate

        self.speech_threshold = speech_threshold
        self.silence_threshold = silence_threshold
        self.silence_duration = silence_duration
        self.chunk_duration = chunk_duration
        self.min_recording = min_recording
        self.vad_cooldown = vad_cooldown
        self.max_recording = max_recording

        self.pre_roll_duration = pre_roll_duration

        self.chunk_size = int(sample_rate * chunk_duration)
        self.max_silence_chunks = int(silence_duration / chunk_duration)
        self.cooldown_chunks = int(vad_cooldown / chunk_duration)
        self.pre_roll_chunks = int(pre_roll_duration / chunk_duration)  # 🆕

    def _calculate_rms(self, audio_chunk: np.ndarray) -> float:
        if len(audio_chunk) == 0:
            return 0.0
        chunk_float = audio_chunk.astype(np.float32)
        mean_square = np.mean(chunk_float ** 2)
        if np.isnan(mean_square) or np.isinf(mean_square):
            return 0.0
        rms = np.sqrt(mean_square)
        return rms if not np.isnan(rms) else 0.0

    def get_rms_chunk(self):
        chunk_size = int(self.sample_rate * self.chunk_duration)
        with sd.InputStream(samplerate=self.sample_rate, channels=1, dtype='int16', blocksize=chunk_size) as stream:
            chunk, _ = stream.read(self.chunk_size)
            chunk = chunk.flatten().astype(np.int16)
            rms = self._calculate_rms(chunk)

            return rms if not np.isnan(rms) else 0

    def _record_with_vad(self) -> bytes:
        pre_roll_buffer = deque(maxlen=self.pre_roll_chunks)

        audio_chunks = []
        silence_counter = 0
        cooldown_counter = 0
        started_recording = False
        vad_active = False
        start_time = None

        with sd.InputStream(
                samplerate=self.sample_rate,
                channels=1,
                dtype='int16',
                blocksize=self.chunk_size
        ) as stream:

            while True:
                chunk, _ = stream.read(self.chunk_size)
                chunk = chunk.flatten().astype(np.int16)
                rms = self._calculate_rms(chunk)

                if not started_recording:
                    pre_roll_buffer.append(chunk.tobytes())

                    if rms > self.speech_threshold:
                        started_recording = True
                        start_time = time.time()
                        audio_chunks.extend(pre_roll_buffer)
                    continue

                audio_chunks.append(chunk.tobytes())

                if not vad_active:
                    cooldown_counter += 1
                    if cooldown_counter >= self.cooldown_chunks:
                        vad_active = True
                    continue

                if rms < self.silence_threshold:
                    silence_counter += 1
                    if silence_counter >= self.max_silence_chunks:
                        print(f"⏸️ Тишина ({silence_counter} чанков), останавливаю")
                        break
                else:
                    silence_counter = 0

                if start_time and (time.time() - start_time) > self.max_recording:
                    break

        min_chunks = int(self.min_recording / self.chunk_duration)
        if len(audio_chunks) < min_chunks:
            return None

        return b''.join(audio_chunks)

    def _save_wav(self, audio_bytes: bytes, filepath: str) -> None:
        with wave.open(filepath, 'wb') as wf:
            wf.setnchannels(1)
            wf.setsampwidth(2)
            wf.setframerate(self.sample_rate)
            wf.writeframes(audio_bytes)

    def listen(self, use_google: bool = True) -> str:
        temp_file = None
        try:
            audio_bytes = self._record_with_vad()
            if audio_bytes is None:
                return ""

            temp_file = tempfile.NamedTemporaryFile(delete=False, suffix='.wav')
            temp_file.close()
            self._save_wav(audio_bytes, temp_file.name)

            with sr.AudioFile(temp_file.name) as source:
                audio = self.recognizer.record(source)

            if use_google:
                text = self.recognizer.recognize_google(audio, language=self.language)
            else:
                text = self.recognizer.recognize_sphinx(audio, language='ru-RU')

            return text

        except sr.UnknownValueError:
            return ""
        except sr.RequestError as e:
            return ""
        except Exception as e:
            return ""
        finally:
            if temp_file and os.path.exists(temp_file.name):
                os.remove(temp_file.name)

@component
@registry.register(component_type=ComponentType.COMPONENT, name="VOICE_HELPER")
class VoiceHelper:
    def __init__(self, prefer_native=True):
        self.voice = 'ru-RU-DmitryNeural'
        self.prefer_native = prefer_native
        self.is_playing = False

    def _is_mobile_device(self) -> bool:
        return (
                'ANDROID_ARGUMENT' in os.environ or
                'KIVY_ANDROID' in os.environ or
                'KIVY_IOS' in os.environ
        )

    def speak(self, text: str) -> None:
        if not text.strip():
            return

        if self.prefer_native:
            self._speak_native(text)

    def _speak_native(self, text: str) -> None:
        try:
            native_tts.speak(text)
            self.is_playing = True
            print(f"🔊 Нативный TTS: {text[:50]}...")
        except NotImplementedError:
            print("⚠️ Нативный TTS не поддерживается этой ОС")
        except Exception as e:
            print(f"❌ Ошибка нативного TTS: {e}")

    def stop(self) -> None:
        try:
            native_tts.stop()
            self.is_playing = False
        except:
            pass
