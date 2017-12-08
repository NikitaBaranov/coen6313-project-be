package com.NikitaBaranov.coen6313projectbe.domain.dto;

public class RecognitionResults {
    RecognitionResult[] predictions;

    public RecognitionResult[] getPredictions() {
        return predictions;
    }

    public void setPredictions(RecognitionResult[] predictions) {
        this.predictions = predictions;
    }
}
