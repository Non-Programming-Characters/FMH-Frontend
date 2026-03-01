import app

from utils.voice_helper import VoiceHelper

if __name__ == "__main__":

    voice = VoiceHelper()
    voice.speak("Привет друг")
    app.run()