package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui.BasicInfoForm;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui.RegisterRoleSelection;

public class RegisterViewModel extends ViewModelConfig {

    public RegisterViewModel() {
        setRegisterRoleSelection(new RegisterRoleSelection());
        setNode(registerRoleSelection);
    }

    private RegisterRoleSelection registerRoleSelection;

    public  RegisterRoleSelection getRegisterRoleSelection() {
        return registerRoleSelection;
    }

    public  void setRegisterRoleSelection(RegisterRoleSelection registerRoleSelection) {
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
