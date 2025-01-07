package com.Group3.foodorderingsystem.Module.Platform.Vendor;

import java.util.HashMap;
import java.util.Map;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Common.settings.model.SettingsViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Assets.VendorNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Assets.VendorTopNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Home.model.HomeViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.model.MenuViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.model.VoucherViewModel;

import javafx.application.Platform;
import javafx.scene.Node;

public class VendorViewModel {

    private static VendorViewModel instance;

    public static VendorViewModel setInstance(VendorViewModel instance) {
        return VendorViewModel.instance = instance;
    }

    public VendorViewModel() {
        instance = this;

        instance.mainFrame = new VendorMainFrame();
        instance.selectedNavigation = VendorNavigationEnum.Home;

        init();
        initHomeViewModel();
        initMenuViewModel();
        initSettingsViewModel();
        initVoucherViewModel();
    }

    public static void clear() {
        instance = null;
    }

    /**
     * Navigate to the selected node
     * 
     * @param node
     */
    public static void navigate(Node node) {
        instance.mainFrame.setCurrentPane(node);
    }

    private VendorMainFrame mainFrame;

    public static VendorMainFrame getMainFrame() {
        return instance.mainFrame;
    }

    public static void setMainFrame(VendorMainFrame mainFrame) {
        instance.mainFrame = mainFrame;
    }

    /**
     * @var navigationList - List of navigation items (bottom navigation bar)
     * @var selectedNavigation - Selected navigation item - control which one is
     *      highlighted
     */
    private VendorNavigationEnum[] navigationList = VendorNavigationEnum.values();
    private VendorNavigationEnum selectedNavigation;

    /**
     * Get the navigation list - bottom one
     * 
     * @return CustomerNavigationEnum[]
     */
    public static VendorNavigationEnum[] getNavigationList() {
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
    private Map<VendorNavigationEnum, VendorTopNavigationEnum> selectedTopNavigation = new HashMap<>();

    /**
     * Initialize the top navigation
     * 
     * Take the first item from the top navigation list and set it as selected
     */
    private void init() {
        if (selectedTopNavigation == null) {
            selectedTopNavigation = new HashMap<>();
        }

        for (VendorNavigationEnum navigation : getNavigationList()) {
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
    public static void setSelectedNavigation(VendorNavigationEnum navigationEnum) {
        instance.selectedNavigation = navigationEnum;

        getSelectedTopNavigation().getAction().run();
    }

    public static void setSelectedNavigation(VendorTopNavigationEnum topNavigationEnum) {
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
    public static VendorNavigationEnum getSelectedNavigation() {
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
    public static VendorTopNavigationEnum getSelectedTopNavigation() {
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

    private MenuViewModel menuViewModel;

    public static MenuViewModel getMenuViewModel() {
        return instance.menuViewModel;
    }

    public static void setMenuViewModel(MenuViewModel menuViewModel) {
        instance.menuViewModel = menuViewModel;
    }

    public static void initMenuViewModel() {
        instance.menuViewModel = new MenuViewModel();
        instance.menuViewModel.init();
    }

    private SettingsViewModel settingsViewModel;

    public static SettingsViewModel getSettingsViewModel() {
        return instance.settingsViewModel;
    }

    public static void setSettingsViewModel(SettingsViewModel settingsViewModel) {
        instance.settingsViewModel = settingsViewModel;
    }

    public static void initSettingsViewModel() {
        Object vendorFromSession = SessionUtil.getVendorFromSession();
        if (vendorFromSession instanceof User) {
            User user = (User) vendorFromSession;
            instance.settingsViewModel = new SettingsViewModel(user.getId());
        } else {
            throw new IllegalStateException("Vendor from session is not a User instance.");
        }
        instance.settingsViewModel.init();
    }

    private VoucherViewModel voucherViewModel;

    public static VoucherViewModel getVoucherViewModel() {
        return instance.voucherViewModel;
    }

    public static void setVoucherViewModel(VoucherViewModel voucherViewModel) {
        instance.voucherViewModel = voucherViewModel;
    }

    public static void initVoucherViewModel() {
        instance.voucherViewModel = new VoucherViewModel();
        instance.voucherViewModel.init();
    }
}
