package com.kacperbieganek.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "toughnessList")
public class ToughnessList {

    @XmlElement(name = "toughness", type = Toughness.class)
    private List<Toughness> toughnessList;

    public ToughnessList() {
        toughnessList = new ArrayList<>();
    }

    public ToughnessList(List<Toughness> toughnessList) {
        this.toughnessList = toughnessList;
    }

    public List<Toughness> getToughnessList() {
        return toughnessList;
    }

    public void setToughnessList(List<Toughness> toughnessList) {
        this.toughnessList = toughnessList;
    }
}
