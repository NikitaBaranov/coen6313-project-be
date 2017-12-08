package com.NikitaBaranov.coen6313projectbe.domain.dto;

public class RecognitionResult {

    public double[] probabilities;
    public int classes;

    public double[] getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(double[] probabilities) {
        this.probabilities = probabilities;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }
}
