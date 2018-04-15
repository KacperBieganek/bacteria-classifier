package com.kacperbieganek.model;

import java.util.Objects;

public class Toughness {
    private int betaGene;
    private int gammaGene;
    private String rank;

    public int getBetaGene() {
        return betaGene;
    }

    public void setBetaGene(int betaGene) {
        this.betaGene = betaGene;
    }

    public int getGammaGene() {
        return gammaGene;
    }

    public void setGammaGene(int gammaGene) {
        this.gammaGene = gammaGene;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Toughness)) return false;
        Toughness toughness = (Toughness) o;
        return getBetaGene() == toughness.getBetaGene() &&
                getGammaGene() == toughness.getGammaGene();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getBetaGene(), getGammaGene());
    }
}
