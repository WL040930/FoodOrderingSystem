package com.Group3.foodorderingsystem.Module.Common.settings.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Module.Common.settings.ui.SettingsPage;

public class SettingsViewModel extends ViewModelConfig {
    
    private User user; 
    public SettingsViewModel(String userId) {
        super();
        this.user = UserServices.findUserById(userId);
    }

    public void init() {
        this.settingsPage = new SettingsPage(user);
        setNode(settingsPage);
    }

    private SettingsPage settingsPage;

    public SettingsPage getSettingsPage() {
        return settingsPage;
    }
}
