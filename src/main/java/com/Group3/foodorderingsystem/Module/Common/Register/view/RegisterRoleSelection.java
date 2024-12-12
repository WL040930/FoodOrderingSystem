/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.Group3.foodorderingsystem.Module.Common.Register.view;

import com.Group3.foodorderingsystem.Core.Util.Colors;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.Router;
import com.Group3.foodorderingsystem.Core.Widget.Header;
import com.Group3.foodorderingsystem.Module.Common.Register.viewmodel.RegisterViewModel;

public class RegisterRoleSelection extends javax.swing.JFrame {

    private RegisterViewModel registerViewModel = new RegisterViewModel();

    public RegisterRoleSelection() {
        registerViewModel.init();

        initComponents();
        setResizable(false); 
        colorInitializer();
        roleController(); 
    } 

    private void colorInitializer() {
        backgroundPanel.setBackground(Colors.blue_1);
    }

    private void roleController() {
        rightIcon.setBorder(null);
        leftIcon.setBorder(null);
        rolePicture.setBorder(null);

        rightIcon.setIcon(Images.getImage("right_arrow.png", 40, 40));
        leftIcon.setIcon(Images.getImage("left_arrow.png", 40, 40));

        rolePicture.setIcon(Images.getImage(registerViewModel.getSelectedRole().imagePath, 234, 234));
        roleField.setText(registerViewModel.getSelectedRole().roleName);
        descriptionField.setText("<html>" + registerViewModel.getSelectedRole().description + "</html>");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        javax.swing.JPanel header = new Header();
        leftIcon = new javax.swing.JLabel();
        rightIcon = new javax.swing.JLabel();
        rolePicture = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        roleField = new javax.swing.JLabel();
        descriptionField = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backgroundPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        leftIcon.setText("leftIcon");
        leftIcon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        leftIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        leftIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leftIconMouseClicked(evt);
            }
        });

        rightIcon.setText("rightIcon");
        rightIcon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rightIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rightIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rightIconMouseClicked(evt);
            }
        });

        rolePicture.setText("picture");
        rolePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel1.setText("You have Selected:");

        roleField.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        roleField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleField.setText("ROLE");

        descriptionField.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        descriptionField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descriptionField.setText("description");
        descriptionField.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        nextButton.setText("Next");
        nextButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jLabel1))
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(descriptionField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                                        .addComponent(leftIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(rolePicture, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(roleField, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addComponent(rightIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nextButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rolePicture, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rightIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(leftIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roleField)
                .addGap(18, 18, 18)
                .addComponent(descriptionField, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        Router.navigate(this, new RegisterInfo());
    }//GEN-LAST:event_nextButtonActionPerformed

    private void leftIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leftIconMouseClicked
        registerViewModel.reorderRoles(-1);
        roleController();
    }//GEN-LAST:event_leftIconMouseClicked

    private void rightIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightIconMouseClicked
        registerViewModel.reorderRoles(1);
        roleController();
    }//GEN-LAST:event_rightIconMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JLabel descriptionField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel leftIcon;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel rightIcon;
    private javax.swing.JLabel roleField;
    private javax.swing.JLabel rolePicture;
    // End of variables declaration//GEN-END:variables
}
