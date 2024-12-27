package com.Group3.foodorderingsystem.Module.Platform.Admin;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class NewFeaturePanel extends JPanel {
    private JFXPanel fxPanel;

    public NewFeaturePanel() {
        initializeFXPanel();
    }

    private void initializeFXPanel() {
        fxPanel = new JFXPanel(); // This will create the JavaFX thread.
        setLayout(new BorderLayout()); // Use BorderLayout in Swing.
        add(fxPanel, BorderLayout.CENTER); // Add JFXPanel to this Swing panel.

        // Create JavaFX Scene on JavaFX Application Thread
        Platform.runLater(() -> {
            NewFeatureFXPanel newFeatureFXPanel = new NewFeatureFXPanel(); // Assuming this is modified to work as a VBox
            Scene scene = new Scene(newFeatureFXPanel, 500, 400); // Size adjusted to match the JavaFX design
            fxPanel.setScene(scene);
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();
        // Re-initialize the FXPanel when the panel is added to the container
        Platform.runLater(() -> {
            NewFeatureFXPanel newFeatureFXPanel = new NewFeatureFXPanel(); // Assuming this is modified to work as a VBox
            Scene scene = new Scene(newFeatureFXPanel, 500, 400); // Size adjusted to match the JavaFX design
            fxPanel.setScene(scene);
        });
    }
}