package ch.dreilaenderhack.tulpe;

import com.google.cloud.texttospeech.v1beta1.SynthesizeSpeechResponse;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeechController {

    SpeechService speechService;

    public SpeechController(SpeechService speechService){
        this.speechService = speechService;
    }

    @PostMapping(value = "/speech")
    public ResponseEntity<byte[]> translateNew(@RequestBody SpeechRequest speechRequest) {
        SynthesizeSpeechResponse respone_speech =  speechService.speakText(speechRequest.getText(),speechRequest.getLanguage());
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(respone_speech.toByteArray(), headers, HttpStatus.OK);
        return responseEntity;

    }
}