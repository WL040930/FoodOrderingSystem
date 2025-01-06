package com.Group3.foodorderingsystem.Module.Platform.Admin.Database.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Database.ui.AdminDatabase;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Database.ui.EditUserInformation;

import javafx.scene.Node;

public class DatabaseViewModel extends ViewModelConfig {
    
    @Override
    public void navigate(Node node) {
        setNode(node);
        AdminViewModel.navigate(AdminViewModel.getDatabaseViewModel().getNode());
    }

    public DatabaseViewModel(){
        super();
    }

    public void init() {
        adminDatabase = new AdminDatabase();
        setNode(adminDatabase);
    }

    private AdminDatabase adminDatabase;

    public AdminDatabase getAdminDatabase() {
        return adminDatabase;
    }

    public void setAdminDatabase(AdminDatabase adminDatabase) {
        this.adminDatabase = adminDatabase;
    }

    private EditUserInformation editUserInformation;

    public EditUserInformation getEditUserInformation() {
        return editUserInformation;
    }

    public void setEditUserInformation(EditUserInformation editUserInformation) {
        this.editUserInformation = editUserInformation;
    }
}
