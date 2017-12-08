package com.NikitaBaranov.coen6313projectbe.domain.dto;

import org.springframework.data.annotation.Id;

public class RecognitionEntity {

    @Id
    public String id;

    public Double[] image;
    public RecognitionResult recognitionResult;


    public Double[] getImage() {
        return image;
    }

    public void setImage(Double[] image) {
        this.image = image;
    }

    public RecognitionResult getRecognitionResult() {
        return recognitionResult;
    }

    public void setRecognitionResult(RecognitionResult recognitionResult) {
        this.recognitionResult = recognitionResult;
    }
}
