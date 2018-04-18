package com.kacperbieganek.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "examinedList")
public class ExaminedList {

    @XmlElement(name = "examined", type = Examined.class)
    private List<Examined> examinedList;

    public ExaminedList() {
        examinedList = new ArrayList<>();
    }

    public ExaminedList(List<Examined> examinedList) {
        this.examinedList = examinedList;
    }

}
