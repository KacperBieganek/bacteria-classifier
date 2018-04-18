package com.kacperbieganek.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bacteriaList")
public class BacteriaList {
    @XmlElement(name = "bacteria", type = Bacteria.class)
    private List<Bacteria> bacteriaList;

    public BacteriaList() {
    }

    public BacteriaList(List<Bacteria> bacteriaList) {
        this.bacteriaList = bacteriaList;
    }

    public List<Bacteria> getBacteriaList() {
        return bacteriaList;
    }

    public void setBacteriaList(List<Bacteria> bacteriaList) {
        this.bacteriaList = bacteriaList;
    }
}
