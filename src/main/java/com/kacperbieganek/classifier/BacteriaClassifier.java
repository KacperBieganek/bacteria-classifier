package com.kacperbieganek.classifier;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.kacperbieganek.dao.BacteriaDao;
import com.kacperbieganek.model.Bacteria;
import com.kacperbieganek.model.Examined;
import com.kacperbieganek.model.Flagella;
import com.kacperbieganek.model.Toughness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BacteriaClassifier {
    private Table<Integer, Integer, Integer> flagellaTable;
    private Table<Integer, Integer, String> toughnessTable;
    private Map<String, String> examinedMap;
    private List<Flagella> flagellaList;
    private List<Toughness> toughnessList;
    private List<Examined> examinedList;
    private List<Flagella> newFlagellaList;
    private List<Toughness> newToughnessList;
    private List<Examined> newExaminedList;
    private BacteriaDao bacteriaDao;

    public BacteriaClassifier(BacteriaDao bacteriaDao) {
        flagellaTable = HashBasedTable.create();
        toughnessTable = HashBasedTable.create();
        examinedMap = new HashMap<>();
        newFlagellaList = new ArrayList<>();
        newExaminedList = new ArrayList<>();
        newToughnessList = new ArrayList<>();
        this.bacteriaDao = bacteriaDao;
        this.toughnessList = bacteriaDao.getAllToughness();
        this.examinedList = bacteriaDao.getAllExamined();
        this.flagellaList = bacteriaDao.getAllFlagella();
        initContainers();
    }

    public void classify(List<Bacteria> bacteriaList) {
        for (Bacteria bacteria : bacteriaList) {
            String genotype = bacteria.getGenotype();
            if (examinedMap.get(genotype) != null)
                continue;

            int alpha = Integer.parseInt(genotype.substring(0, 1) + genotype.substring(5, 6));
            int beta = Integer.parseInt(genotype.substring(1, 2) + genotype.substring(4, 5));
            int gamma = Integer.parseInt(genotype.substring(2, 3) + genotype.substring(3, 4));
            int number;
            String rank;
            String bacteriaClass;
            number = flagellaTable.get(alpha, beta) == null ?
                    classifyFlagella(alpha, beta) :
                    flagellaTable.get(alpha, beta);
            rank = toughnessTable.get(beta, gamma) == null ?
                    classifyToughness(beta, gamma) :
                    toughnessTable.get(beta, gamma);

            bacteriaClass = number + rank;
            newExaminedList.add(new Examined(genotype, bacteriaClass));
        }
        updateDatabase();
        classifyCleanUp();
    }

    private int classifyFlagella(int alpha, int beta) {
        int number = 0;
        double minDistance = Double.MAX_VALUE;
        for (Flagella flagella : flagellaList) {
            double distance = Math.sqrt(Math.pow(alpha - flagella.getAlphaGene(), 2) + Math.pow(beta - flagella.getBetaGene(), 2));
            if (distance < minDistance) {
                minDistance = distance;
                number = flagella.getNumber();
            }
        }
        flagellaTable.put(alpha, beta, number);
        newFlagellaList.add(new Flagella(alpha, beta, number));

        return number;
    }

    private String classifyToughness(int beta, int gamma) {
        String rank = "";
        double minDistance = Double.MAX_VALUE;
        for (Toughness toughness : toughnessList) {
            double distance = Math.sqrt(Math.pow(beta - toughness.getBetaGene(), 2) + Math.pow(gamma - toughness.getGammaGene(), 2));
            if (distance < minDistance) {
                minDistance = distance;
                rank = toughness.getRank();
            }
        }
        toughnessTable.put(beta, gamma, rank);
        newToughnessList.add(new Toughness(beta, gamma, rank));

        return rank;
    }

    public List<Flagella> getFlagellaList() {
        return this.flagellaList;
    }

    public List<Toughness> getToughnessList() {
        return this.toughnessList;
    }

    public List<Examined> getExaminedList() {
        return this.examinedList;
    }

    private void updateDatabase() {
        bacteriaDao.insertAllExamined(newExaminedList);
        bacteriaDao.insertAllFlagella(newFlagellaList);
        bacteriaDao.insertAllToughness(newToughnessList);
    }

    private void classifyCleanUp() {
        examinedList.addAll(newExaminedList);
        flagellaList.addAll(newFlagellaList);
        toughnessList.addAll(newToughnessList);
        newExaminedList.clear();
        newFlagellaList.clear();
        newToughnessList.clear();
    }

    private void initContainers() {
        for (Examined examined : this.examinedList) {
            examinedMap.put(examined.getBacteriaGenotype(), examined.getBacteriaClass());
        }
        for (Flagella flagella : this.flagellaList) {
            flagellaTable.put(flagella.getAlphaGene(), flagella.getBetaGene(), flagella.getNumber());
        }
        for (Toughness toughness : this.toughnessList) {
            toughnessTable.put(toughness.getBetaGene(), toughness.getGammaGene(), toughness.getRank());
        }
    }

}
