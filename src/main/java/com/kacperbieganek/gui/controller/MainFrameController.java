package com.kacperbieganek.gui.controller;

import com.kacperbieganek.classifier.BacteriaClassifier;
import com.kacperbieganek.dao.BacteriaDao;
import com.kacperbieganek.dao.BacteriaDaoImpl;
import com.kacperbieganek.dao.ConnectionManager;
import com.kacperbieganek.gui.view.MainFrame;
import com.kacperbieganek.model.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFrameController {

    private static final String XML_EXAMINEDFILE_PATH = "src/main/resources/examined.xml";
    private static final String XML_FLAGELLAFILE_PATH = "src/main/resources/flagella.xml";
    private static final String XML_TOUGHNESSFILE_PATH = "src/main/resources/toughness.xml";

    private MainFrame mainFrame;
    private JTable examinedTable;
    private JTable flagellaTable;
    private JTable toughnessTable;
    private JButton exportToXMLButton;
    private JButton loadBacteriasButton;
    private JButton classifyButton;
    private ConnectionManager connectionManager;
    private BacteriaDao bacteriaDao;
    private DefaultTableModel examinedTableModel;
    private DefaultTableModel flagellaTableModel;
    private DefaultTableModel toughnessTableModel;
    private BacteriaClassifier bacteriaClassifier;
    private List<Bacteria> bacteriasToClassify;
    private XMLSerializer xmlSerializer;

    public MainFrameController(ConnectionManager cm) {
        connectionManager = cm;
        mainFrame = new MainFrame();
        bacteriaDao = new BacteriaDaoImpl(cm.getConnection());
        bacteriaClassifier = new BacteriaClassifier(bacteriaDao);
        bacteriasToClassify = new ArrayList<>();
        xmlSerializer = new XMLSerializer();
        initComponents();
        initListeners();
        setupTableModels();
        mainFrame.addWindowListener(getWindowAdapter());
        mainFrame.setVisible(true);
    }

    private void initComponents() {
        examinedTable = mainFrame.getExaminedTable();
        flagellaTable = mainFrame.getFlagellaTable();
        toughnessTable = mainFrame.getToughnessTable();
        exportToXMLButton = mainFrame.getExportToXMLButton();
        loadBacteriasButton = mainFrame.getLoadBacteriasButton();
        classifyButton = mainFrame.getClassifyButton();
    }

    private void initListeners() {

        classifyButton.addActionListener(al -> {
            bacteriaClassifier.classify(bacteriasToClassify);
            bacteriasToClassify.clear();
            updateExaminedTable();
            updateFlagellaTable();
            updateToughnessTable();
        });

        loadBacteriasButton.addActionListener(al -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "xml file", "xml");
            chooser.setFileFilter(filter);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/development"));
            int returnVal = chooser.showOpenDialog(mainFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                bacteriasToClassify = xmlSerializer.readBacteria(new File(chooser.getSelectedFile().getAbsolutePath()));
            }
        });

        exportToXMLButton.addActionListener(al -> serializeInBackground());

    }

    private WindowAdapter getWindowAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to quit?");
                if (option == 0) {
                    connectionManager.closeConnection();
                    System.exit(0);
                }
            }
        };
    }

    private void setupTableModels() {
        setupExaminedTableModel();
        setupFlagellaTableModel();
        setupToughnessTableModel();
    }

    private void setupExaminedTableModel() {
        examinedTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        examinedTableModel.setColumnCount(2);
        examinedTableModel.setColumnIdentifiers(new String[]{"GENOTYPE", "CLASS"});
        examinedTable.setModel(examinedTableModel);
        updateExaminedTable();
    }

    private void setupFlagellaTableModel() {
        flagellaTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        flagellaTableModel.setColumnCount(3);
        flagellaTableModel.setColumnIdentifiers(new String[]{"ALPHA", "BETA", "NUMBER"});
        flagellaTable.setModel(flagellaTableModel);
        updateFlagellaTable();
    }

    private void setupToughnessTableModel() {
        toughnessTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        toughnessTableModel.setColumnCount(3);
        toughnessTableModel.setColumnIdentifiers(new String[]{"BETA", "GAMMA", "RANK"});
        toughnessTable.setModel(toughnessTableModel);
        updateToughnessTable();
    }

    private void updateExaminedTable() {
        clearTable(examinedTableModel);
        List<Examined> examinedList = bacteriaClassifier.getExaminedList();
        for (Examined examined : examinedList) {
            examinedTableModel.addRow(new Object[]{examined.getBacteriaGenotype(), examined.getBacteriaClass()});
        }
        examinedTableModel.fireTableDataChanged();
    }

    private void updateFlagellaTable() {
        clearTable(flagellaTableModel);
        List<Flagella> flagellaList = bacteriaClassifier.getFlagellaList();
        for (Flagella flagella : flagellaList) {
            flagellaTableModel.addRow(new Object[]{flagella.getAlphaGene(), flagella.getBetaGene(), flagella.getNumber()});
        }
        flagellaTableModel.fireTableDataChanged();
    }

    private void updateToughnessTable() {
        clearTable(toughnessTableModel);
        List<Toughness> toughnessList = bacteriaClassifier.getToughnessList();
        for (Toughness toughness : toughnessList) {
            toughnessTableModel.addRow(new Object[]{toughness.getBetaGene(), toughness.getGammaGene(), toughness.getRank()});
        }
    }

    private void clearTable(DefaultTableModel tableModel) {
        int rowCount = tableModel.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private void serializeInBackground() {
        Runnable serializeExaminedList = () -> xmlSerializer.saveExamined(
                new ExaminedList(bacteriaClassifier.getExaminedList()),
                new File(XML_EXAMINEDFILE_PATH));

        new Thread(serializeExaminedList).start();

        Runnable serializeFlagellaList = () -> xmlSerializer.saveFlagella(
                new FlagellaList(bacteriaClassifier.getFlagellaList()),
                new File(XML_FLAGELLAFILE_PATH));

        new Thread(serializeFlagellaList).start();

        Runnable serializeToughnessList = () -> xmlSerializer.saveToughness(
                new ToughnessList(bacteriaClassifier.getToughnessList()),
                new File(XML_TOUGHNESSFILE_PATH));

        new Thread(serializeToughnessList).start();

    }
}
