package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui.BasicInfoForm;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui.RegisterRoleSelection;

public class RegisterViewModel extends ViewModelConfig {
    
    private static RegisterViewModel instance; 

    public RegisterViewModel() {
        instance = this;
        setRegisterRoleSelection(new RegisterRoleSelection());
    }

    private RegisterRoleSelection registerRoleSelection;

    public static RegisterRoleSelection getRegisterRoleSelection() {
        return instance.registerRoleSelection;
    }

    public static void setRegisterRoleSelection(RegisterRoleSelection registerRoleSelection) {
        instance.registerRoleSelection = registerRoleSelection;
    }

    private BasicInfoForm basicInfoForm; 

    public static BasicInfoForm getBasicInfoForm() {
        return instance.basicInfoForm;
    }

    public static void setBasicInfoForm(BasicInfoForm basicInfoForm) {
        instance.basicInfoForm = basicInfoForm;
    }
}
