package ch.dreilaenderhack.tulpe;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

class Translator {

    String translate(String inputLanguage, String outputLanguage, String text) {
        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translation translation =
                translate.translate(
                        text,
                        Translate.TranslateOption.sourceLanguage(inputLanguage),
                        Translate.TranslateOption.targetLanguage(outputLanguage));

        String translatedText = translation.getTranslatedText();

        TargetLanguage targetLanguage = TargetLanguage.fromAbkuerzung(inputLanguage);

        if (targetLanguage != null) {
            translatedText = new FachbegriffTranslation().cleanUpFachbegriffe(targetLanguage, translatedText);
        }

        return translatedText;
    }
}