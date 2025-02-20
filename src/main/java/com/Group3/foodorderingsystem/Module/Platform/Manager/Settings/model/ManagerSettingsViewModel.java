package com.Group3.foodorderingsystem.Module.Platform.Manager.Settings.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Settings.ui.ManagerSettingsUI;

import javafx.scene.Node;

public class ManagerSettingsViewModel extends ViewModelConfig {
    
    @Override
    public void navigate(Node node) {
        setNode(node);
        ManagerViewModel.navigate(node);
    }

    public ManagerSettingsViewModel() {
        this.managerSettingsUI = new ManagerSettingsUI();
        setNode(managerSettingsUI);
    }

    private ManagerSettingsUI managerSettingsUI;

    public ManagerSettingsUI getManagerSettingsUI() {
        return managerSettingsUI;
    }

    public void setManagerSettingsUI(ManagerSettingsUI managerSettingsUI) {
        this.managerSettingsUI = managerSettingsUI;
    }
}
