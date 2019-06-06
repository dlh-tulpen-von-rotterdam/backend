package ch.dreilaenderhack.tulpe;

import com.google.api.client.util.Beta;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.v3beta1.TranslateTextResponse;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TranslateController {

    Translator translator;

    public TranslateController(Translator translator){
        this.translator = translator;
    }

    @PostMapping(value = "/translate")
    public String translateNew(@RequestBody TranslateRequest translateRequest) {
        TranslateTextResponse translateResultSBB = translator.translateText("rock-objective-231114", "us-central1", translateRequest.getText(), translateRequest.getInputLanguage(), translateRequest.getOutputLanguage(),"sbb");
        TranslateTextResponse translateResultINT = translator.translateText("rock-objective-231114", "us-central1", translateRequest.getText(), translateRequest.getInputLanguage(), translateRequest.getOutputLanguage(),"int");
        Map<String,String> ret = new HashMap<>();
        ret.put("translated_text",translateResultSBB.getTranslations(0).getTranslatedText());
        ret.put("glossary_sbb",translateResultSBB.getGlossaryTranslations(0).getTranslatedText());
        ret.put("glossary_int",translateResultINT.getGlossaryTranslations(0).getTranslatedText());

        return new Gson().toJson(ret);
    }

    @GetMapping("/glossaries/create")
    public String createGlossary() {
        BetaTranslation.createGlossary("rock-objective-231114","us-central1","int","gs://tulpen/glossary-int.csv");
        return "ok";
    }
}