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

    /**
     * Singleton instance of the AdminViewModel
     */
    private static AdminViewModel instance;

    /**
     * Constructor
     */
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
     * Navigate to the selected node
     * 
     * @param node
     */
    public static void navigate(Node node) {
        instance.adminMainFrame.setCurrentPane(node);
    }

    /**
     * AdminMainFrame instance - all the element will be added to this frame
     */
    private AdminMainFrame adminMainFrame;

    /**
     * Get the instance of the AdminViewModel
     * 
     * @return AdminViewModel
     */
    public static AdminMainFrame getAdminMainFrame() {
        return instance.adminMainFrame;
    }

    /**
     * Set the instance of the AdminViewModel
     * 
     * @param adminMainFrame
     */
    public static void setAdminMainFrame(AdminMainFrame adminMainFrame) {
        instance.adminMainFrame = adminMainFrame;
    }

    /**
     * @var navigationList - List of navigation items (bottom navigation bar)
     * @var selectedNavigation - Selected navigation item - control which one is
     *      highlighted
     */
    private AdminNavigationEnum[] navigationList = AdminNavigationEnum.values();
    private AdminNavigationEnum selectedNavigation;

    /**
     * Get the navigation list - bottom one
     * 
     * @return AdminNavigationEnum[]
     */
    public static AdminNavigationEnum[] getNavigationList() {
        return instance.navigationList;
    }

    /**
     * @var selectedTopNavigation - Selected top navigation item - control which one
     *      is highlighted
     * 
     *      This is a map of AdminNavigationEnum and AdminTopNavigationEnum
     *      which means that for example in this page i have 4 totals of
     *      AdminNavigationEnum
     *      and each of them have their own AdminTopNavigationEnum
     * 
     *      User -> Register
     *      Settings -> Settings
     * 
     *      and so on ..
     */
    private Map<AdminNavigationEnum, AdminTopNavigationEnum> selectedTopNavigation = new HashMap<>();

    /**
     * Initialize the top navigation
     * 
     * Take the first item from the top navigation list and set it as selected
     */
    private void init() {
        if (selectedTopNavigation == null) {
            selectedTopNavigation = new HashMap<>();
        }

        for (AdminNavigationEnum navigation : getNavigationList()) {
            selectedTopNavigation.put(navigation, navigation.getTopNavigationItem().get(0));
        }
    }

    /**
     * Set the selected navigation
     * 
     * @param adminNavigationEnum
     * @param adminTopNavigationEnum
     * 
     *                               This is the key method to control which page is
     *                               being displayed in the scrollpanel
     * 
     *                               Modify the AdminTopNavigationEnum to adjust the
     *                               entry point
     */
    public static void setSelectedNavigation(AdminNavigationEnum adminNavigationEnum) {
        instance.selectedNavigation = adminNavigationEnum;

        getSelectedTopNavigation().getAction().run();
    }

    public static void setSelectedNavigation(AdminTopNavigationEnum adminTopNavigationEnum) {
        instance.selectedTopNavigation.put(getSelectedNavigation(), adminTopNavigationEnum);
        adminTopNavigationEnum.getAction().run();

        Platform.runLater(() -> {
            if (instance.adminMainFrame != null) {
                instance.adminMainFrame.updateHeader();
            }
        });
    }

    /**
     * Get the selected navigation
     * 
     * @return AdminNavigationEnum
     */
    public static AdminNavigationEnum getSelectedNavigation() {
        return instance.selectedNavigation;
    }

    /**
     * Get the selected top navigation based on the selected navigation
     * 
     * @return AdminTopNavigationEnum
     * 
     *         For example, if u provide [User]
     *         it will return [Register] as the default entry point
     */
    public static AdminTopNavigationEnum getSelectedTopNavigation() {
        return instance.selectedTopNavigation.get(getSelectedNavigation());
    }

    // User section methods

    // TODO: CONVERT IT INTO SINGLE VIEW MODEL
    private AdminRegister adminRegister;
    private AdminDatabase adminDatabase;

    // TODO: CONVERT IT INTO SINGLE VIEW MODEL
    private SettingsPage settingsPage;

    public static AdminRegister getAdminRegister() {
        return instance.adminRegister;
    }

    public static void setAdminRegister(AdminRegister adminRegister) {
        instance.adminRegister = adminRegister;
    }

    public static AdminDatabase getAdminDatabase() {
        return instance.adminDatabase;
    }

    public static void setAdminDatabase(AdminDatabase adminDatabase) {
        instance.adminDatabase = adminDatabase;
    }

    // Settings section methods
    public static SettingsPage getSettingsPage() {
        return instance.settingsPage;
    }

    public static void setSettingsPage(SettingsPage settingsPage) {
        instance.settingsPage = settingsPage;
    }

}
