package ch.dreilaenderhack.tulpe;

import com.google.cloud.translate.v3beta1.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BetaTranslation {

    static Glossary createGlossary(String projectId, String location, String name, String gcsUri) {
        try (TranslationServiceClient translationServiceClient = TranslationServiceClient.create()) {

            LocationName locationName =
                    LocationName.newBuilder().setProject(projectId).setLocation(location).build();
            //Glossary.LanguageCodesSet languageCodesSet =
            //        Glossary.LanguageCodesSet.newBuilder().addLanguageCodes("it").addLanguageCodes("fr").addLanguageCodes("de").build();
            Glossary.LanguageCodesSet languageCodesSet =
                            Glossary.LanguageCodesSet.newBuilder()
                                    .addAllLanguageCodes(Arrays.asList("en","de-at","da","fr-fr","sl","it-it","nl-be","nl-nl","fr-be","sk","it-ch","de-ch","fr-ch","sv-se","hu"))
                                    .build();

            GcsSource gcsSource = GcsSource.newBuilder().setInputUri(gcsUri).build();
            GlossaryInputConfig glossaryInputConfig =
                    GlossaryInputConfig.newBuilder().setGcsSource(gcsSource).build();
            GlossaryName glossaryName =
                    GlossaryName.newBuilder()
                            .setProject(projectId)
                            .setLocation(location)
                            .setGlossary(name)
                            .build();
            Glossary glossary =
                    Glossary.newBuilder()
                            .setLanguageCodesSet(languageCodesSet)
                            .setInputConfig(glossaryInputConfig)
                            .setName(glossaryName.toString())
                            .build();
            CreateGlossaryRequest request =
                    CreateGlossaryRequest.newBuilder()
                            .setParent(locationName.toString())
                            .setGlossary(glossary)
                            .build();

            // Call the API
            Glossary response =
                    translationServiceClient.createGlossaryAsync(request).get(300, TimeUnit.SECONDS);
            System.out.format("Created: %s\n", response.getName());
            System.out.format("Input Uri: %s\n", response.getInputConfig().getGcsSource());
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Couldn't create client.", e);
        }
    }

}
