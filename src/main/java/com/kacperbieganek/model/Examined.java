package com.kacperbieganek.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "examined")
public class Examined {
    private String bacteriaGenotype;
    private String bacteriaClass;

    public Examined() {
    }

    public Examined(String bacteriaGenotype, String bacteriaClass) {
        this.bacteriaGenotype = bacteriaGenotype;
        this.bacteriaClass = bacteriaClass;
    }

    public String getBacteriaGenotype() {
        return bacteriaGenotype;
    }

    @XmlElement(name = "genotype")
    public void setBacteriaGenotype(String bacteriaGenotype) {
        this.bacteriaGenotype = bacteriaGenotype;
    }

    public String getBacteriaClass() {
        return bacteriaClass;
    }

    @XmlElement(name = "class")
    public void setBacteriaClass(String bacteriaClass) {
        this.bacteriaClass = bacteriaClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Examined)) return false;
        Examined examined = (Examined) o;
        return Objects.equals(getBacteriaGenotype(), examined.getBacteriaGenotype()) &&
                Objects.equals(getBacteriaClass(), examined.getBacteriaClass());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getBacteriaGenotype(), getBacteriaClass());
    }
}
