package com.Group3.foodorderingsystem.Module;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.Appearance;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;

public class SetupViewModel {
    private static SetupViewModel instance; // Singleton instance
    private User user;
    private Appearance appearance;

    private SetupViewModel() {
        this.appearance = new Appearance(false);
    }

    public static SetupViewModel getInstance() {
        if (instance == null) {
            instance = new SetupViewModel();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }
}
