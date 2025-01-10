package com.Group3.foodorderingsystem.Module.Platform.Admin;

import java.util.HashMap;
import java.util.Map;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Common.Notification.model.NotificationViewModel;
import com.Group3.foodorderingsystem.Module.Common.settings.model.SettingsViewModel;
import com.Group3.foodorderingsystem.Module.Common.settings.ui.SettingsPage;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Assets.AdminNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Assets.AdminTopNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Database.model.DatabaseViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Database.ui.AdminDatabase;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.model.FinanceViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.model.RegisterViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Requests.model.RequestsViewModel;

import javafx.application.Platform;
import javafx.scene.Node;

public class AdminViewModel {

    /**
     * Singleton instance of the AdminViewModel
     */
    private static AdminViewModel instance;

    public static AdminViewModel setInstance(AdminViewModel instance) {
        return AdminViewModel.instance = instance;
    }

    /**
     * Constructor
     */
    public AdminViewModel() {
        instance = this;

        instance.adminMainFrame = new AdminMainFrame();
        instance.selectedNavigation = AdminNavigationEnum.User;

        init();
        initRegisterViewModel();
        initSettingsViewModel();
        initDatabaseViewModel();
        initFinanceViewModel();
        initNotificationViewModel();
        initRequestsViewModel();
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

    /**
     * @var registerViewModel - RegisterViewModel instance
     */
    private RegisterViewModel registerViewModel;

    /**
     * Get the RegisterViewModel instance
     * 
     * @return RegisterViewModel
     */
    public static RegisterViewModel getRegisterViewModel() {
        return instance.registerViewModel;
    }

    /**
     * Set the RegisterViewModel instance
     * 
     * @param registerViewModel
     */
    public static void setRegisterViewModel(RegisterViewModel registerViewModel) {
        instance.registerViewModel = registerViewModel;
    }

    /**
     * Generate new instance of the AdminViewModel
     */

    public static void initRegisterViewModel() {
        instance.registerViewModel = new RegisterViewModel();
        instance.registerViewModel.init();
    }

    private DatabaseViewModel databaseViewModel;

    public static void initDatabaseViewModel() {
        instance.databaseViewModel = new DatabaseViewModel();
        instance.databaseViewModel.init();
    }

    public static DatabaseViewModel getDatabaseViewModel() {
        return instance.databaseViewModel;
    }

    public static void setDatabaseViewModel(DatabaseViewModel databaseViewModel) {
        instance.databaseViewModel = databaseViewModel;
    }

    private SettingsViewModel settingsViewModel;

    public static void initSettingsViewModel() {
        Object adminFromSession = SessionUtil.getAdminFromSession();
        if (adminFromSession instanceof User) {
            User user = (User) adminFromSession;
            instance.settingsViewModel = new SettingsViewModel(user.getId());
        } else {
            throw new IllegalStateException("Admin from session is not a User instance.");
        }
        instance.settingsViewModel.init();
    }

    public static SettingsViewModel getSettingsViewModel() {
        return instance.settingsViewModel;
    }

    public static void setSettingsViewModel(SettingsViewModel settingsViewModel) {
        instance.settingsViewModel = settingsViewModel;
    }

    private FinanceViewModel financeViewModel;

    public static void initFinanceViewModel() {
        instance.financeViewModel = new FinanceViewModel();
        instance.financeViewModel.init();
    }

    public static FinanceViewModel getFinanceViewModel() {
        return instance.financeViewModel;
    }

    public static void setFinanceViewModel(FinanceViewModel financeViewModel) {
        instance.financeViewModel = financeViewModel;
    }

    private NotificationViewModel notificationViewModel;

    public static void initNotificationViewModel() {
        Object admin = SessionUtil.getAdminFromSession();
        if (admin instanceof User) {
            User user = (User) admin;
            instance.notificationViewModel = new NotificationViewModel(user.getId());
        } else {
            throw new IllegalStateException("Admin from session is not a User instance.");
        }
        instance.notificationViewModel.init();
    }

    public static NotificationViewModel getNotificationViewModel() {
        return instance.notificationViewModel;
    }

    public static void setNotificationViewModel(NotificationViewModel notificationViewModel) {
        instance.notificationViewModel = notificationViewModel;
    }

    private RequestsViewModel requestsViewModel;

    public static void initRequestsViewModel() {
        instance.requestsViewModel = new RequestsViewModel();
        instance.requestsViewModel.init();
    }

    public static RequestsViewModel getRequestsViewModel() {
        return instance.requestsViewModel;
    }

    public static void setRequestsViewModel(RequestsViewModel requestsViewModel) {
        instance.requestsViewModel = requestsViewModel;
    }

}
