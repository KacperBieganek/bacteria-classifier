package com.kacperbieganek.gui.view;

import javax.swing.*;

public class MainFrame extends JFrame{

    private static final int BASE_WIDTH = 500;
    private static final int BASE_HEIGHT = 500;
    private JPanel mainPanel;
    private JTabbedPane tablesTabbedPane;
    private JPanel flagellaPanel;
    private JPanel toughtnessPanel;
    private JPanel examinedPanel;
    private JTable examinedTable;
    private JTable flagellaTable;
    private JTable toughnessTable;
    private JButton exportToXMLButton;
    private JButton loadBacteriasButton;
    private JButton classifyButton;

    public MainFrame() {
        super("Bacteria classifier app");
        setSize(BASE_WIDTH,BASE_HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public JButton getExportToXMLButton() {
        return exportToXMLButton;
    }

    public JButton getLoadBacteriasButton() {
        return loadBacteriasButton;
    }

    public JButton getClassifyButton() {
        return classifyButton;
    }

    public JTable getExaminedTable() {
        return examinedTable;
    }

    public JTable getFlagellaTable() {
        return flagellaTable;
    }

    public JTable getToughnessTable() {
        return toughnessTable;
    }
}
