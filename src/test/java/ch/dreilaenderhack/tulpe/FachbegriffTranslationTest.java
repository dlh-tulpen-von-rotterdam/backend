package ch.dreilaenderhack.tulpe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FachbegriffTranslationTest {

    @Test
    public void test(){
        String s = new FachbegriffTranslation().cleanUpFachbegriffe(TargetLanguage.EN, "My problem is Immediate with something");

        assertEquals("My problem is Ad hoc with something", s);
    }

}