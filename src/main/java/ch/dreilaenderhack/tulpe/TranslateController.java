package ch.dreilaenderhack.tulpe;

import com.google.cloud.translate.Translate;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslateController {

    Translator translator;

    public TranslateController(Translator translator){
        this.translator = translator;
    }

    @PostMapping(value = "/translate")
    public String translate(@RequestBody TranslateRequest translateRequest) {
        TranslateResult translateResult = translator.translate(translateRequest.getInputLanguage(), translateRequest.getOutputLanguage(),
                translateRequest.getText());

        return new Gson().toJson(translateResult);
    }
}