package com.kacperbieganek.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "flagella")
public class Flagella {
    private int alphaGene;
    private int betaGene;
    private int number;

    public Flagella() {
    }

    public Flagella(int alphaGene, int betaGene, int number) {
        this.alphaGene = alphaGene;
        this.betaGene = betaGene;
        this.number = number;
    }

    public int getAlphaGene() {
        return alphaGene;
    }

    @XmlElement(name = "alpha")
    public void setAlphaGene(int alphaGene) {
        this.alphaGene = alphaGene;
    }

    public int getBetaGene() {
        return betaGene;
    }

    @XmlElement(name = "beta")
    public void setBetaGene(int betaGene) {
        this.betaGene = betaGene;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flagella)) return false;
        Flagella flagella = (Flagella) o;
        return getAlphaGene() == flagella.getAlphaGene() &&
                getBetaGene() == flagella.getBetaGene();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAlphaGene(), getBetaGene());
    }
}
