package ch.dreilaenderhack.tulpe;

public class TranslateRequest {
    private String inputLanguage;
    private String outputLanguage;
    private String text;

    public String getInputLanguage() {
        return inputLanguage;
    }

    public void setInputLanguage(String inputLanguage) {
        this.inputLanguage = inputLanguage;
    }

    public String getOutputLanguage() {
        return outputLanguage;
    }

    public void setOutputLanguage(String outputLanguage) {
        this.outputLanguage = outputLanguage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
