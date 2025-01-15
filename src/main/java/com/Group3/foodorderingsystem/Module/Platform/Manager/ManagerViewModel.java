package com.Group3.foodorderingsystem.Module.Platform.Manager;

import java.util.HashMap;
import java.util.Map;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Common.Notification.model.NotificationViewModel;
import com.Group3.foodorderingsystem.Module.Common.Transaction.model.TransactionViewModel;
import com.Group3.foodorderingsystem.Module.Common.settings.model.SettingsViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Assets.ManagerNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Assets.ManagerTopNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Assets.RunnerNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Assets.RunnerTopNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Home.model.RunnerHomeViewModel;

import javafx.application.Platform;
import javafx.scene.Node;

public class ManagerViewModel {

    private static ManagerViewModel instance;

    public static ManagerViewModel setInstance(ManagerViewModel instance) {
        return ManagerViewModel.instance = instance;
    }

    public ManagerViewModel() {
        instance = this;

        instance.mainFrame = new ManagerMainFrame();
        instance.selectedNavigation = ManagerNavigationEnum.Home;

        // instance.orderViewModel.init();

        init();
    }

    /**
     * Navigate to the selected node
     * 
     * @param node
     */
    public static void navigate(Node node) {
        instance.mainFrame.setCurrentPane(node);
    }

    private ManagerMainFrame mainFrame;

    public static ManagerMainFrame getMainFrame() {
        return instance.mainFrame;
    }

    public static void setMainFrame(ManagerMainFrame mainFrame) {
        instance.mainFrame = mainFrame;
    }

    /**
     * @var navigationList - List of navigation items (bottom navigation bar)
     * @var selectedNavigation - Selected navigation item - control which one is
     *      highlighted
     */
    private ManagerNavigationEnum[] navigationList = ManagerNavigationEnum.values();
    private ManagerNavigationEnum selectedNavigation;

    /**
     * Get the navigation list - bottom one
     * 
     * @return CustomerNavigationEnum[]
     */
    public static ManagerNavigationEnum[] getNavigationList() {
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
    private Map<ManagerNavigationEnum, ManagerTopNavigationEnum> selectedTopNavigation = new HashMap<>();

    /**
     * Initialize the top navigation
     * 
     * Take the first item from the top navigation list and set it as selected
     */
    private void init() {
        if (selectedTopNavigation == null) {
            selectedTopNavigation = new HashMap<>();
        }

        for (ManagerNavigationEnum navigation : getNavigationList()) {
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
    public static void setSelectedNavigation(ManagerNavigationEnum navigationEnum) {
        instance.selectedNavigation = navigationEnum;

        getSelectedTopNavigation().getAction().run();
    }

    public static void setSelectedNavigation(ManagerTopNavigationEnum topNavigationEnum) {
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
    public static ManagerNavigationEnum getSelectedNavigation() {
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
    public static ManagerTopNavigationEnum getSelectedTopNavigation() {
        return instance.selectedTopNavigation.get(getSelectedNavigation());
    }

}
