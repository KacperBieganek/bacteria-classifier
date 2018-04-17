package com.kacperbieganek.gui.view;

import javax.swing.*;

public class LoginFrame extends JFrame {

    private static final int BASE_WIDTH = 500;
    private static final int BASE_HEIGHT = 500;
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JButton databaseSelectButton;
    private JButton connectButton;

    public LoginFrame() {
        super("Bacteria classifier app");
        setSize(BASE_WIDTH, BASE_HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public JButton getDatabaseSelectButton() {
        return databaseSelectButton;
    }

    public JButton getConnectButton() {
        return connectButton;
    }
}
