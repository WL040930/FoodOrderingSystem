package com.Group3.foodorderingsystem.Module.Platform.Admin;

import java.util.HashMap;
import java.util.Map;

import com.Group3.foodorderingsystem.Module.Common.settings.SettingsPage;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Assets.AdminNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Assets.AdminTopNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Database.AdminDatabase;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.AdminRegister;

import javafx.application.Platform;
import javafx.scene.Node;

public class AdminViewModel {
    private static AdminViewModel instance;

    private AdminMainFrame adminMainFrame;

    public static AdminMainFrame getAdminMainFrame() {
        return instance.adminMainFrame;
    }

    public static void setAdminMainFrame(AdminMainFrame adminMainFrame) {
        instance.adminMainFrame = adminMainFrame;
    }

    // for bottom navigation bar
    private AdminNavigationEnum[] navigationList = AdminNavigationEnum.values();

    public static AdminNavigationEnum[] getNavigationList() {
        return instance.navigationList;
    }

    private AdminNavigationEnum selectedNavigation;

    public static AdminNavigationEnum getSelectedNavigation() {
        return instance.selectedNavigation;
    }

    public static void setSelectedNavigation(AdminNavigationEnum adminNavigationEnum) {
        instance.selectedNavigation = adminNavigationEnum;

        getSelectedTopNavigation().getAction().run();
    }

    // for top navigation bar
    private Map<AdminNavigationEnum, AdminTopNavigationEnum> selectedTopNavigation = new HashMap<>();

    private void init() {
        if (selectedTopNavigation == null) {
            selectedTopNavigation = new HashMap<>();
        }

        for (AdminNavigationEnum navigation : getNavigationList()) {
            selectedTopNavigation.put(navigation, navigation.getTopNavigationItem().get(0)); // Get the first top
        } // AdminTopNavigationEnum
    }

    public static AdminTopNavigationEnum getSelectedTopNavigation() {
        return instance.selectedTopNavigation.get(getSelectedNavigation());
    }

    public static void setSelectedTopNavigation(AdminTopNavigationEnum adminTopNavigationEnum) {
        instance.selectedTopNavigation.put(getSelectedNavigation(), adminTopNavigationEnum);

        adminTopNavigationEnum.getAction().run();

        Platform.runLater(() -> {
            if (instance.adminMainFrame != null) {
                instance.adminMainFrame.updateHeader();
            }
        });
    }

    // for User Section
    private AdminRegister adminRegister;

    public static AdminRegister getAdminRegister() {
        return instance.adminRegister;
    }

    public static void setAdminRegister(AdminRegister adminRegister) {
        instance.adminRegister = adminRegister;
    }

    private AdminDatabase adminDatabase;

    public static AdminDatabase getAdminDatabase() {
        return instance.adminDatabase;
    }

    public static void setAdminDatabase(AdminDatabase adminDatabase) {
        instance.adminDatabase = adminDatabase;
    }

    // for Settings Section
    private SettingsPage settingsPage;

    public static SettingsPage getSettingsPage() {
        return instance.settingsPage;
    }

    public static void setSettingsPage(SettingsPage settingsPage) {
        instance.settingsPage = settingsPage;
    }

    public AdminViewModel() {
        instance = this;
        instance.adminMainFrame = new AdminMainFrame();
        instance.selectedNavigation = AdminNavigationEnum.User;

        instance.adminRegister = new AdminRegister();
        instance.adminDatabase = new AdminDatabase();
        instance.settingsPage = new SettingsPage();

        init();
    }

    /**
     * For managing the panel in admin main frame
     *
     * @param node - insert the page u wish to replace in the scroll panel
     */
    public static void navigate(Node node) {
        instance.adminMainFrame.setCurrentPane(node);
    }

    public static void reset(AdminNavigationEnum selectedNavigation) {
        if (selectedNavigation == getSelectedNavigation()) {
            Platform.runLater(() -> {
                switch (selectedNavigation) {
                    case User:
                        instance.adminRegister = new AdminRegister();
                        instance.adminDatabase = new AdminDatabase();
                        setSelectedTopNavigation(selectedNavigation.getTopNavigationItem().get(0));
                        navigate(instance.adminRegister); // Ensure immediate navigation
                        break;
                    case Settings:
                        instance.settingsPage = new SettingsPage();
                        navigate(instance.settingsPage); // Ensure immediate navigation
                        break;
                    default:
                        break;
                }
            });
        }
    }
}
