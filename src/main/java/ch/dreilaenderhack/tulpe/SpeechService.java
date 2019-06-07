package ch.dreilaenderhack.tulpe;

import com.google.cloud.texttospeech.v1beta1.*;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.v3beta1.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
class SpeechService {

    Translate gTranslate;
    TextToSpeechClient textToSpeechClient;


    @PostConstruct
    public void init(){
        try {
            textToSpeechClient = TextToSpeechClient.create();
        } catch (IOException e){
            throw new RuntimeException("Credentials file not found.", e);
        }
    }

    SynthesizeSpeechResponse speakText(String text, String language) {

        SynthesisInput input = SynthesisInput.newBuilder()
                .setText(text)
                .build();

        // Build the voice request, select the language code ("en-US") and the ssml voice gender
        // ("neutral")
        VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                .setLanguageCode(language)
                .setName(language+"-Wavenet-A")
                .build();

        // Select the type of audio file you want returned
        AudioConfig audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.MP3)
                .build();

        // Perform the text-to-speech request on the text input with the selected voice parameters and
        // audio file type
        SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice,
                audioConfig);
        return response;

    }

}