package ch.dreilaenderhack.tulpe;

enum TargetLanguage {
    DE("de"),
    EN("en"),
    HU("hu"),
    FR("fr");
    private String abkuerzung;

    TargetLanguage(String abkuerzung) {
        this.abkuerzung = abkuerzung;
    }

    static TargetLanguage fromAbkuerzung(String abkuerzung) {
        for (TargetLanguage targetLanguage : TargetLanguage.values()) {
            if (targetLanguage.abkuerzung.equals(abkuerzung)) {
                return targetLanguage;
            }
        }

        return null;
    }
}
