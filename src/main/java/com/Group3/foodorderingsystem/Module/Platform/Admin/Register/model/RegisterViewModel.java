package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.model;

import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.assets.RegisterAssets;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui.BasicInfoForm;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui.RegisterRoleSelection;

import javafx.scene.Node;

public class RegisterViewModel extends ViewModelConfig {

    public void navigate(Node node) {
        setNode(node);

        AdminViewModel.navigate(node);
    }

    /**
     * Constructor
     */
    public RegisterViewModel() {
        setSelectedRole(registerAssets.get(0));
    }

    /*
     * Initialize the RegisterViewModel
     */
    public void init() { 
        registerRoleSelection = new RegisterRoleSelection();
        setNode(registerRoleSelection);
    }

    private List<RegisterAssets> registerAssets = List.of(RegisterAssets.values());
    private RegisterAssets selectedRole;

    public List<RegisterAssets> getRegisterAssets() {
        return registerAssets;
    }

    public RegisterAssets getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(int index) {
        int currentIndex = selectedRole == null ? 0 : registerAssets.indexOf(selectedRole);
        currentIndex = (currentIndex + index + registerAssets.size()) % registerAssets.size();
        selectedRole = registerAssets.get(currentIndex);
    }

    public void setSelectedRole(RegisterAssets selectedRole) {
        this.selectedRole = selectedRole;
    }

    private RegisterRoleSelection registerRoleSelection;

    public RegisterRoleSelection getRegisterRoleSelection() {
        return registerRoleSelection;
    }

    public void setRegisterRoleSelection(RegisterRoleSelection registerRoleSelection) {
        this.registerRoleSelection = registerRoleSelection;
    }

    private BasicInfoForm basicInfoForm;

    public BasicInfoForm getBasicInfoForm() {
        return this.basicInfoForm;
    }

    public void setBasicInfoForm(BasicInfoForm basicInfoForm) {
        this.basicInfoForm = basicInfoForm;
    }
}
