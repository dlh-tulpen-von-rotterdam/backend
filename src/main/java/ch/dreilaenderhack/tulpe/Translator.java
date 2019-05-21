package ch.dreilaenderhack.tulpe;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateException;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

class Translator {

    TranslateResult translate(String inputLanguage, String outputLanguage, String text) {

        String translatedText = null;
        String cleanedUpText = null;
        GoogleJsonError googleJsonError = null;
        // Instantiates a client
        try {
            Translate translate = TranslateOptions.getDefaultInstance().getService();

            Translation translation =
                    translate.translate(
                            text,
                            Translate.TranslateOption.sourceLanguage(inputLanguage),
                            Translate.TranslateOption.targetLanguage(outputLanguage));

            translatedText = translation.getTranslatedText();

            TargetLanguage targetLanguage = TargetLanguage.fromAbkuerzung(outputLanguage);

            if (targetLanguage != null) {
                cleanedUpText = new FachbegriffTranslation().cleanUpFachbegriffe(targetLanguage, translatedText);
            }
        } catch (TranslateException e) {
            if (e.getCause() instanceof GoogleJsonResponseException) {
                googleJsonError = ((GoogleJsonResponseException) e.getCause()).getDetails();
            }
        }

        return new TranslateResult(cleanedUpText, translatedText, googleJsonError);
    }
}