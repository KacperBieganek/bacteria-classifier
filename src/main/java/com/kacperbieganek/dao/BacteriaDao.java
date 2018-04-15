package com.kacperbieganek.dao;

import com.kacperbieganek.model.Examined;
import com.kacperbieganek.model.Flagella;
import com.kacperbieganek.model.Toughness;

import java.util.List;

public interface BacteriaDao {
    List<Examined> getAllExamined();
    Examined getExamined(String genotype);
    boolean insertExamined(Examined examined);
    boolean insertAllExamined(List<Examined> examinedList);

    List<Flagella> getAllFlagella();
    Flagella getFlagella(int alphaGene, int betaGene);
    boolean insertFlagella(Flagella flagella);
    boolean insertAllFlagella(List<Flagella> flagellaList);

    List<Toughness> getAllToughness();
    Toughness getToughness(int betaGene, int gammaGene);
    boolean insertToughness(Toughness toughness);
    boolean insertAllToughness(List<Toughness> toughnessList);


}
