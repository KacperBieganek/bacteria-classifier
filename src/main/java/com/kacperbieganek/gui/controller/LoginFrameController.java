package com.kacperbieganek.gui.controller;

import com.google.common.base.Strings;
import com.kacperbieganek.dao.ConnectionManager;
import com.kacperbieganek.gui.view.LoginFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class LoginFrameController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginFrameController.class);
    private LoginFrame loginFrame;
    private JButton databaseSelectButton;
    private JButton connectButton;
    private String databaseCanonicalPath;
    private ConnectionManager cm;

    public LoginFrameController() {
        loginFrame = new LoginFrame();
        initComponents();
        initListeners();
        loginFrame.setVisible(true);
        cm = ConnectionManager.getInstance();
    }

    private void initComponents() {
        databaseSelectButton = loginFrame.getDatabaseSelectButton();
        connectButton = loginFrame.getConnectButton();
    }

    private void initListeners() {

        databaseSelectButton.addActionListener(al -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Database", "db");
            chooser.setFileFilter(filter);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/development"));
            int returnVal = chooser.showOpenDialog(loginFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                databaseCanonicalPath = chooser.getSelectedFile().getAbsolutePath();
            }
        });

        connectButton.addActionListener(al -> {
            if (!Strings.isNullOrEmpty(databaseCanonicalPath)) {
                cm.createConnection(databaseCanonicalPath);
                new MainFrameController(cm);
                loginFrame.setVisible(false);
                loginFrame.dispose();
            }
        });
    }
}
