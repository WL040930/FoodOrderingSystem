package com.Group3.foodorderingsystem.Module.Platform.Customer;

import java.util.HashMap;
import java.util.Map;


import com.Group3.foodorderingsystem.Module.Common.settings.SettingsPage;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Assets.CustomerNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Assets.CustomerTopNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.model.HomeViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.model.OrderViewModel;

import javafx.application.Platform;
import javafx.scene.Node;

public class CustomerViewModel {

    private static CustomerViewModel instance;
    private OrderViewModel orderViewModel;

    public CustomerViewModel() {
        instance = this;

        instance.customerMainFrame = new CustomerMainFrame();
        instance.selectedNavigation = CustomerNavigationEnum.Home;

        instance.settingsPage = new SettingsPage();
        instance.orderViewModel = new OrderViewModel();
        instance.orderViewModel.init();

        init();
        initHomeViewModel();
    }

    /**
     * Navigate to the selected node
     * 
     * @param node
     */
    public static void navigate(Node node) {
        instance.customerMainFrame.setCurrentPane(node);
    }

    private CustomerMainFrame customerMainFrame;

    public static CustomerMainFrame getCustomerMainFrame() {
        return instance.customerMainFrame;
    }

    public static void setCustomerMainFrame(CustomerMainFrame customerMainFrame) {
        instance.customerMainFrame = customerMainFrame;
    }

    /**
     * @var navigationList - List of navigation items (bottom navigation bar)
     * @var selectedNavigation - Selected navigation item - control which one is
     *      highlighted
     */
    private CustomerNavigationEnum[] navigationList = CustomerNavigationEnum.values();
    private CustomerNavigationEnum selectedNavigation; 

    /**
     * Get the navigation list - bottom one
     * 
     * @return CustomerNavigationEnum[]
     */
    public static CustomerNavigationEnum[] getNavigationList() {
        return instance.navigationList;
    }

    
    public static OrderViewModel getOrderViewModel() {
        return instance.orderViewModel;
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
    private Map<CustomerNavigationEnum, CustomerTopNavigationEnum> selectedTopNavigation = new HashMap<>();

    /**
     * Initialize the top navigation
     * 
     * Take the first item from the top navigation list and set it as selected
     */
    private void init() {
        if (selectedTopNavigation == null) {
            selectedTopNavigation = new HashMap<>();
        }

        for (CustomerNavigationEnum navigation : getNavigationList()) {
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
    public static void setSelectedNavigation(CustomerNavigationEnum navigationEnum) {
        instance.selectedNavigation = navigationEnum;

        getSelectedTopNavigation().getAction().run();
    }

    public static void setSelectedNavigation(CustomerTopNavigationEnum topNavigationEnum) {
        instance.selectedTopNavigation.put(getSelectedNavigation(), topNavigationEnum);
        topNavigationEnum.getAction().run();

        Platform.runLater(() -> {
            if (instance.customerMainFrame != null) {
                instance.customerMainFrame.updateHeader();
            }
        });
    }

    /**
     * Get the selected navigation
     * 
     * @return CustomerNavigationEnum
     */
    public static CustomerNavigationEnum getSelectedNavigation() {
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
    public static CustomerTopNavigationEnum getSelectedTopNavigation() {
        return instance.selectedTopNavigation.get(getSelectedNavigation());
    }

    private HomeViewModel homeViewModel;

    public static HomeViewModel getHomeViewModel() {
        return instance.homeViewModel;
    }

    public static void setHomeViewModel(HomeViewModel homeViewModel) {
        instance.homeViewModel = homeViewModel;
    }

    public static void initHomeViewModel() {
        instance.homeViewModel = new HomeViewModel();
        instance.homeViewModel.init();
    }

    private SettingsPage settingsPage;

    public static SettingsPage getSettingsPage() {
        return instance.settingsPage;
    }

    public static void setSettingsPage(SettingsPage settingsPage) {
        instance.settingsPage = settingsPage;
    }
}
