package com.kacperbieganek.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class XMLSerializer {

    private static final Logger LOG = LoggerFactory.getLogger(XMLSerializer.class);


    public void saveExamined(List<Examined> examinedList, File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Examined.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(examinedList, file);
        } catch (JAXBException e) {
            LOG.error("", e);
        }
    }

    public void saveFlagella(List<Flagella> flagellaList, File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Flagella.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(flagellaList, file);
        } catch (JAXBException e) {
            LOG.error("", e);
        }
    }

    public void saveToughness(List<Toughness> toughnessList, File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Toughness.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(toughnessList, file);
        } catch (JAXBException e) {
            LOG.error("", e);
        }
    }

    public List<Bacteria> readBacteria(File file) {
        BacteriaList bacteriaList;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(BacteriaList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            bacteriaList = (BacteriaList) unmarshaller.unmarshal(file);
            return bacteriaList.getBacteriaList();
        } catch (JAXBException e) {
            LOG.error("", e);
        }
        return Collections.emptyList();
    }


}
