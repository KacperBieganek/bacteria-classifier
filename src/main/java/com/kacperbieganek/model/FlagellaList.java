package com.kacperbieganek.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "flagellaList")
public class FlagellaList {

    @XmlElement(name = "flagella", type = Flagella.class)
    private List<Flagella> flagellaList;

    public FlagellaList() {
        flagellaList = new ArrayList<>();
    }

    public FlagellaList(List<Flagella> flagellaList) {
        this.flagellaList = flagellaList;
    }

}
