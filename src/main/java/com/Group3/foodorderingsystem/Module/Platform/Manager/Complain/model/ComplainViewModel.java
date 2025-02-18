package com.Group3.foodorderingsystem.Module.Platform.Manager.Complain.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Complain.ui.ComplainDetailsUI;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Complain.ui.ComplainListUI;

import javafx.scene.Node;

public class ComplainViewModel extends ViewModelConfig{
    
    private ComplainListUI complainListUI;
    private ComplainDetailsUI complainDetailsUI;

    public void navigate(Node node) {
        setNode(node);

        ManagerViewModel.navigate(node);
    }

    public void init() {
        complainListUI = new ComplainListUI();

        setNode(complainListUI);

    }

    public ComplainViewModel() {
        super();
        
    }

    public ComplainListUI getComplainListUI() {
        return complainListUI;
    }

    public void setComplainListUI(ComplainListUI complainListUI) {
        this.complainListUI = complainListUI;
    }

    public ComplainDetailsUI getComplainDetailsUI() {
        return this.complainDetailsUI;
    }

    public void setComplainDetailsUI(ComplainDetailsUI complainDetailsUI) {
        this.complainDetailsUI = complainDetailsUI;
    }
}
