package ch.dreilaenderhack.tulpe;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class FachbegriffTranslation {
    private static Map<String, String> deTranslatedToRequiredTranslation = new HashMap<>();
    private static Map<String, String> enTranslatedToRequiredTranslation = new HashMap<>();
    private static Map<String, String> huTranslatedToRequiredTranslation = new HashMap<>();

    @Value("${app.corrected_translations}")
    private Resource translationFile;

    @PostConstruct
    public void init() {
        loadCsv(translationFile);
    }

    String cleanUpFachbegriffe(TargetLanguage targetLanguage, String translatedText) {

        String replaced = null;

        if (targetLanguage == TargetLanguage.EN) {
            replaced = replaceWhereNecessary(translatedText, enTranslatedToRequiredTranslation);

        } else if (targetLanguage == TargetLanguage.DE) {
            replaced = replaceWhereNecessary(translatedText, deTranslatedToRequiredTranslation);

        } else if (targetLanguage == TargetLanguage.HU) {
            replaced = replaceWhereNecessary(translatedText, huTranslatedToRequiredTranslation);
        } else {
            replaced = translatedText;
        }

        return replaced;
    }

    private String replaceWhereNecessary(String translatedText, Map<String, String> map) {
        String replaced = translatedText;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (replaced.contains(entry.getKey())) {
                replaced = translatedText.replace(entry.getKey(), entry.getValue());
            }
        }

        return replaced;
    }

    private static void loadCsv(Resource csvFile) {

        try (CSVReader reader = new CSVReader(new FileReader(csvFile.getFile()))){
            String[] line;
            while ((line = reader.readNext()) != null) {
                String english = line[1];
                String german = line[2];
                String hungarian = line[3];
                String en2de = line[4];
                String en2hu = line[5];
                String de2en = line[6];
                String de2hu = line[7];
                String hu2en = line[8];
                String hu2de = line[9];

                if (!en2de.equals(german)) {
                    deTranslatedToRequiredTranslation.put(en2de, german);
                }

                if (!hu2de.equals(german)) {
                    deTranslatedToRequiredTranslation.put(hu2de, german);
                }

                if (!de2en.equals(english)) {
                    enTranslatedToRequiredTranslation.put(de2en, english);
                }

                if (!hu2en.equals(english)) {
                    enTranslatedToRequiredTranslation.put(hu2en, english);
                }

                if (!de2hu.equals(english)) {
                    huTranslatedToRequiredTranslation.put(de2hu, hungarian);
                }

                if (!en2hu.equals(english)) {
                    huTranslatedToRequiredTranslation.put(en2hu, hungarian);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}