/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.Group3.foodorderingsystem.Core.Widget;

import com.Group3.foodorderingsystem.Core.Util.Colors;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminPanel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;

/**
 *
 * @author limwe
 */
public class AdminNavigator extends javax.swing.JPanel {

    /**
     * Creates new form AdminNavigator
     */
    public AdminNavigator() {
        initComponents();
        colorInitializer();
        styleInitializer();
    }

    private void colorInitializer() {
        this.setBackground(Colors.blue_3);
    }

    private void styleInitializer() {
        accountManagementText.setFont(accountManagementText.getFont().deriveFont(0));
        settingsText.setFont(settingsText.getFont().deriveFont(0));
        topUpText.setFont(topUpText.getFont().deriveFont(0));
        receiptText.setFont(receiptText.getFont().deriveFont(0));

        accountManagementIcon.setIcon(Images.getImage("logo.png", 40, 40));
        settingsIcon.setIcon(Images.getImage("logo.png", 40, 40));
        topUpIcon.setIcon(Images.getImage("logo.png", 40, 40));
        receiptIcon.setIcon(Images.getImage("logo.png", 40, 40));

        switch (AdminViewModel.instance.adminPanel) {
            case ACCOUNT_MANAGEMENT:
                accountManagementText.setFont(accountManagementText.getFont().deriveFont(java.awt.Font.BOLD));
                break;
            case SETTINGS:
                settingsText.setFont(settingsText.getFont().deriveFont(java.awt.Font.BOLD));
                break;
            case TOP_UP:
                topUpText.setFont(topUpText.getFont().deriveFont(java.awt.Font.BOLD));
                break;
            case RECEIPT:
                receiptText.setFont(receiptText.getFont().deriveFont(java.awt.Font.BOLD));
                break;
            default:
                break;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accountManagementIcon = new javax.swing.JLabel();
        accountManagementText = new javax.swing.JLabel();
        settingsIcon = new javax.swing.JLabel();
        settingsText = new javax.swing.JLabel();
        topUpIcon = new javax.swing.JLabel();
        receiptIcon = new javax.swing.JLabel();
        topUpText = new javax.swing.JLabel();
        receiptText = new javax.swing.JLabel();

        accountManagementIcon.setText("jLabel1");
        accountManagementIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        accountManagementIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountManagementIconMouseClicked(evt);
            }
        });

        accountManagementText.setText("Management");

        settingsIcon.setText("jLabel1");
        settingsIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        settingsIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsIconMouseClicked(evt);
            }
        });

        settingsText.setText("Settings");

        topUpIcon.setText("jLabel1");
        topUpIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        topUpIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                topUpIconMouseClicked(evt);
            }
        });

        receiptIcon.setText("jLabel2");
        receiptIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        receiptIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                receiptIconMouseClicked(evt);
            }
        });

        topUpText.setText("Top Up");

        receiptText.setText("Receipt");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(accountManagementIcon,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(accountManagementText)))
                                .addGap(88, 88, 88)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(topUpText)
                                        .addComponent(topUpIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104,
                                        Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(receiptText)
                                        .addComponent(receiptIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(91, 91, 91)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(settingsIcon, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(settingsText, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(23, 23, 23)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(accountManagementIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(settingsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(topUpIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(receiptIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(accountManagementText)
                                        .addComponent(settingsText)
                                        .addComponent(topUpText)
                                        .addComponent(receiptText))
                                .addContainerGap(12, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void accountManagementIconMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_accountManagementIconMouseClicked
        if (AdminViewModel.instance.adminPanel == AdminPanel.ACCOUNT_MANAGEMENT) {
            return;
        }
        AdminViewModel.instance.adminPanel = AdminPanel.ACCOUNT_MANAGEMENT;
        styleInitializer();
        AdminViewModel.instance.refresh();
    }// GEN-LAST:event_accountManagementIconMouseClicked

    private void topUpIconMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_topUpIconMouseClicked
        if (AdminViewModel.instance.adminPanel == AdminPanel.TOP_UP) {
            return;
        }
        AdminViewModel.instance.adminPanel = AdminPanel.TOP_UP;
        styleInitializer();
        AdminViewModel.instance.refresh();
    }// GEN-LAST:event_topUpIconMouseClicked

    private void receiptIconMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_receiptIconMouseClicked
        if (AdminViewModel.instance.adminPanel == AdminPanel.RECEIPT) {
            return;
        }
        AdminViewModel.instance.adminPanel = AdminPanel.RECEIPT;
        styleInitializer();
        AdminViewModel.instance.refresh();
    }// GEN-LAST:event_receiptIconMouseClicked

    private void settingsIconMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_settingsIconMouseClicked
        if (AdminViewModel.instance.adminPanel == AdminPanel.SETTINGS) {
            return;
        }
        AdminViewModel.instance.adminPanel = AdminPanel.SETTINGS;
        styleInitializer();
        AdminViewModel.instance.refresh();
    }// GEN-LAST:event_settingsIconMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accountManagementIcon;
    private javax.swing.JLabel accountManagementText;
    private javax.swing.JLabel receiptIcon;
    private javax.swing.JLabel receiptText;
    private javax.swing.JLabel settingsIcon;
    private javax.swing.JLabel settingsText;
    private javax.swing.JLabel topUpIcon;
    private javax.swing.JLabel topUpText;
    // End of variables declaration//GEN-END:variables
}
