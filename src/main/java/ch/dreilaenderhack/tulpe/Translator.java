package ch.dreilaenderhack.tulpe;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.v3beta1.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
class Translator {

    Translate gTranslate;
    TranslationServiceClient translationService;


    @PostConstruct
    public void init(){
        try {
            translationService = TranslationServiceClient.create();
        } catch (IOException e){
            throw new RuntimeException("Credentials file not found.", e);
        }
    }

    /**
     * Translates a given text to a target language.
     *
     * @param projectId - Id of the project.
     * @param location - location name.
     * @param text - Text for translation.
     * @param sourceLanguageCode - Language code of text. e.g. "en"
     * @param targetLanguageCode - Language code for translation. e.g. "sr"
     */
    TranslateTextResponse translateText(
            String projectId,
            String location,
            String text,
            String sourceLanguageCode,
            String targetLanguageCode,
            String glossary) {

            LocationName locationName =
                    LocationName.newBuilder().setProject(projectId).setLocation(location).build();


            GlossaryName glossaryName =
                    GlossaryName.newBuilder()
                            .setProject(projectId)
                            .setLocation(location)
                            .setGlossary(glossary)
                            .build();
            TranslateTextGlossaryConfig translateTextGlossaryConfig =
                    TranslateTextGlossaryConfig.newBuilder().setIgnoreCase(true).setGlossary(glossaryName.toString()).build();

            TranslateTextRequest translateTextRequest =
                    TranslateTextRequest.newBuilder()
                            .setParent(locationName.toString())
                            .setMimeType("text/plain")
                            .setSourceLanguageCode(sourceLanguageCode)
                            .setTargetLanguageCode(targetLanguageCode)
                            .setGlossaryConfig(translateTextGlossaryConfig)
                            .addContents(text)
                            .build();

            // Call the API
            TranslateTextResponse response = translationService.translateText(translateTextRequest);
            return response;

    }

}