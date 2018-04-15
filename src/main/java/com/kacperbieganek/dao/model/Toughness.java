package com.kacperbieganek.dao.model;

public class Toughness {
    private int betaGene;
    private int gameGene;
    private String rank;

    public int getBeta() {
        return betaGene;
    }

    public void setBeta(int betaGene) {
        this.betaGene = betaGene;
    }

    public int getGamma() {
        return gameGene;
    }

    public void setGamma(int gameGene) {
        this.gameGene = gameGene;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
