package com.kacperbieganek.dao.model;

public class Examined {
    private String bacteriaGenotype;
    private String bacteriaClass;

    public String getBacteriaGenotype() {
        return bacteriaGenotype;
    }

    public void setBacteriaGenotype(String bacteriaGenotype) {
        this.bacteriaGenotype = bacteriaGenotype;
    }

    public String getBacteriaClass() {
        return bacteriaClass;
    }

    public void setBacteriaClass(String bacteriaClass) {
        this.bacteriaClass = bacteriaClass;
    }
}
