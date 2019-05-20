package ch.dreilaenderhack.tulpe;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslateController {

    @PostMapping(value = "/translate")
    public String translate(@RequestBody TranslateRequest translateRequest) {
        String translate = new Translator().translate(translateRequest.getInputLanguage(), translateRequest.getOutputLanguage(),
                translateRequest.getText());
        return "{\"text\": \""+ translate + "\"}";
    }

}