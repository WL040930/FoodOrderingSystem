package com.Group3.foodorderingsystem.Module.Platform.Runner;

import java.util.HashMap;
import java.util.Map;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Common.Notification.model.NotificationViewModel;
import com.Group3.foodorderingsystem.Module.Common.Transaction.model.TransactionViewModel;
import com.Group3.foodorderingsystem.Module.Common.settings.model.SettingsViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Assets.RunnerNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Assets.RunnerTopNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Home.model.RunnerHomeViewModel;

import javafx.application.Platform;
import javafx.scene.Node;

public class RunnerViewModel {

    private static RunnerViewModel instance;

    public static RunnerViewModel setInstance(RunnerViewModel instance) {
        return RunnerViewModel.instance = instance;
    }

    public RunnerViewModel() {
        instance = this;

        instance.mainFrame = new RunnerMainFrame();
        instance.selectedNavigation = RunnerNavigationEnum.Home;

        // instance.orderViewModel.init();

        init();
        initHomeViewModel();
        initSettingsViewModel();
        initTransactionViewModel();
        initNotificationViewModel();
    }

    /**
     * Navigate to the selected node
     * 
     * @param node
     */
    public static void navigate(Node node) {
        instance.mainFrame.setCurrentPane(node);
    }

    private RunnerMainFrame mainFrame;

    public static RunnerMainFrame getMainFrame() {
        return instance.mainFrame;
    }

    public static void setMainFrame(RunnerMainFrame mainFrame) {
        instance.mainFrame = mainFrame;
    }

    /**
     * @var navigationList - List of navigation items (bottom navigation bar)
     * @var selectedNavigation - Selected navigation item - control which one is
     *      highlighted
     */
    private RunnerNavigationEnum[] navigationList = RunnerNavigationEnum.values();
    private RunnerNavigationEnum selectedNavigation;

    /**
     * Get the navigation list - bottom one
     * 
     * @return CustomerNavigationEnum[]
     */
    public static RunnerNavigationEnum[] getNavigationList() {
        return instance.navigationList;
    }

    /**
     * @var selectedTopNavigation - Selected top navigation item - control which one
     *      is highlighted
     * 
     *      This is a map of CustomerNavigationEnum and CustomerTopNavigationEnum
     *      which means that for example in this page i have 4 totals of
     *      CustomerNavigationEnum
     *      and each of them have their own CustomerTopNavigationEnum
     * 
     *      User -> Register
     *      Settings -> Settings
     * 
     *      and so on ..
     */
    private Map<RunnerNavigationEnum, RunnerTopNavigationEnum> selectedTopNavigation = new HashMap<>();

    /**
     * Initialize the top navigation
     * 
     * Take the first item from the top navigation list and set it as selected
     */
    private void init() {
        if (selectedTopNavigation == null) {
            selectedTopNavigation = new HashMap<>();
        }

        for (RunnerNavigationEnum navigation : getNavigationList()) {
            selectedTopNavigation.put(navigation, navigation.getTopNavigationItem().get(0));
        }
    }

    /**
     * Set the selected navigation
     * 
     * @param navigationEnum
     * @param topNavigationEnum
     * 
     *                          This is the key method to control which page is
     *                          being displayed in the scrollpanel
     * 
     *                          Modify the CustomerTopNavigationEnum to adjust the
     *                          entry point
     */
    public static void setSelectedNavigation(RunnerNavigationEnum navigationEnum) {
        instance.selectedNavigation = navigationEnum;

        getSelectedTopNavigation().getAction().run();
    }

    public static void setSelectedNavigation(RunnerTopNavigationEnum topNavigationEnum) {
        instance.selectedTopNavigation.put(getSelectedNavigation(), topNavigationEnum);
        topNavigationEnum.getAction().run();

        Platform.runLater(() -> {
            if (instance.mainFrame != null) {
                instance.mainFrame.updateHeader();
            }
        });
    }

    /**
     * Get the selected navigation
     * 
     * @return CustomerNavigationEnum
     */
    public static RunnerNavigationEnum getSelectedNavigation() {
        return instance.selectedNavigation;
    }

    /**
     * Get the selected top navigation based on the selected navigation
     * 
     * @return CustomerTopNavigationEnum
     * 
     *         For example, if u provide [User]
     *         it will return [Register] as the default entry point
     */
    public static RunnerTopNavigationEnum getSelectedTopNavigation() {
        return instance.selectedTopNavigation.get(getSelectedNavigation());
    }

    private RunnerHomeViewModel runnerHomeViewModel;

    public static RunnerHomeViewModel getHomeViewModel() {
        return instance.runnerHomeViewModel;
    }

    public static void setHomeViewModel(RunnerHomeViewModel runnerHomeViewModel) {
        instance.runnerHomeViewModel = runnerHomeViewModel;
    }

    public static void initHomeViewModel() {
        instance.runnerHomeViewModel = new RunnerHomeViewModel();
        instance.runnerHomeViewModel.init();
    }

    private TransactionViewModel transactionViewModel;

    public static TransactionViewModel getTransactionViewModel() {
        return instance.transactionViewModel;
    }

    public static void setTransactionViewModel(TransactionViewModel transactionViewModel) {
        instance.transactionViewModel = transactionViewModel;
    }

    public static void initTransactionViewModel() {
        instance.transactionViewModel = new TransactionViewModel();
        instance.transactionViewModel.init();
    }

    private SettingsViewModel settingsViewModel;

    public static SettingsViewModel getSettingsViewModel() {
        return instance.settingsViewModel;
    }

    public static void setSettingsViewModel(SettingsViewModel settingsViewModel) {
        instance.settingsViewModel = settingsViewModel;
    }

    public static void initSettingsViewModel() {
        Object runner = SessionUtil.getRiderFromSession();
        if (runner instanceof User) {
            User user = (User) runner;
            instance.settingsViewModel = new SettingsViewModel(user.getId());
        } else {
            throw new IllegalStateException("Runner from session is not a User instance.");
        }
        instance.settingsViewModel.init();
    }

    private NotificationViewModel notificationViewModel;

    public static NotificationViewModel getNotificationViewModel() {
        return instance.notificationViewModel;
    }

    public static void setNotificationViewModel(NotificationViewModel notificationViewModel) {
        instance.notificationViewModel = notificationViewModel;
    }

    public static void initNotificationViewModel() {
        Object runner = SessionUtil.getRiderFromSession();
        if (runner instanceof User) {
            User user = (User) runner;
            instance.notificationViewModel = new NotificationViewModel(user.getId());
        } else {
            throw new IllegalStateException("Runner from session is not a User instance.");
        }
        instance.notificationViewModel.init();
    }

}
