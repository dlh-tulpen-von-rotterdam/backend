package ch.dreilaenderhack.tulpe;

import com.google.api.client.googleapis.json.GoogleJsonError;

public class TranslateResult {
    private String text;
    private String originalTranslate;
    private GoogleJsonError googleJsonError;

    public TranslateResult(String text, String originalTranslate, GoogleJsonError googleJsonError) {
        this.text = text;
        this.originalTranslate = originalTranslate;
        this.googleJsonError = googleJsonError;
    }
}
