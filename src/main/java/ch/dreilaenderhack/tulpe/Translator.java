package ch.dreilaenderhack.tulpe;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateException;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
class Translator {

    @Value("${app.credentials}")
    Resource credentials;

    Translate gTranslate;

    FachbegriffTranslation fachbegriffTranslation;

    public Translator(FachbegriffTranslation fachbegriffTranslation){
        this.fachbegriffTranslation = fachbegriffTranslation;
    }

    @PostConstruct
    public void init(){
        try {
            gTranslate = TranslateOptions.newBuilder().setCredentials(ServiceAccountCredentials.fromStream(credentials.getInputStream())).build().getService();
        } catch (IOException e){
            throw new RuntimeException("Credentials file not found.", e);
        }
    }

    TranslateResult translate(String inputLanguage, String outputLanguage, String text) {

        String translatedText = null;
        String cleanedUpText = null;
        GoogleJsonError googleJsonError = null;
        // Instantiates a client
        try {

            Translation translation =
                    gTranslate.translate(
                            text,
                            Translate.TranslateOption.sourceLanguage(inputLanguage),
                            Translate.TranslateOption.targetLanguage(outputLanguage),
                            //explicitely set neuronal translation (should be default)
                            Translate.TranslateOption.model("nmt"));

            translatedText = translation.getTranslatedText();

            TargetLanguage targetLanguage = TargetLanguage.fromAbkuerzung(outputLanguage);

            if (targetLanguage != null) {
                cleanedUpText = fachbegriffTranslation.cleanUpFachbegriffe(targetLanguage, translatedText);
            }

        } catch (TranslateException e) {
            if (e.getCause() instanceof GoogleJsonResponseException) {
                googleJsonError = ((GoogleJsonResponseException) e.getCause()).getDetails();
            }
        }

        return new TranslateResult(cleanedUpText, translatedText, googleJsonError);
    }
}