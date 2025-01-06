package com.Group3.foodorderingsystem.Module.Platform.Vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.BottomNavigationClass;
import com.Group3.foodorderingsystem.Core.Model.Entity.Config.HeaderClass;
import com.Group3.foodorderingsystem.Core.Widgets.BaseMainApplication;
import com.Group3.foodorderingsystem.Core.Widgets.BottomNavigation;
import com.Group3.foodorderingsystem.Core.Widgets.Header;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Assets.VendorNavigationEnum;

import javafx.scene.Node;

public class VendorMainFrame extends BaseMainApplication {

    @Override
    protected String sceneHeader() {
        return "Vendor Panel";
    }

    @Override
    protected Node defaultContent() {
        return VendorViewModel.getHomeViewModel().getNode();
    }

    @Override
    protected Header buildHeader() {
        VendorNavigationEnum navigationList = VendorViewModel.getSelectedNavigation();
        Header header = new Header(
                navigationList.getTopNavigationItem().stream()
                        .map(e -> new HeaderClass(
                                e.getTitle(),
                                VendorViewModel.getSelectedTopNavigation() == e,
                                () -> {
                                    VendorViewModel.setSelectedNavigation(e);
                                    updateHeader();
                                    e.getAction().run();
                                }))
                        .collect(Collectors.toList()));

        return header;
    }

    @Override
    protected BottomNavigation buildBottomNavigator() {
        List<BottomNavigationClass> config = new ArrayList<>();

        for (VendorNavigationEnum component : VendorViewModel.getNavigationList()) {
            config.add(new BottomNavigationClass(
                    component.getTitle(),
                    component.getIcon(),
                    () -> handleNavigation(component),
                    VendorViewModel.getSelectedNavigation() == component));
        }

        BottomNavigation bottomNavigation = new BottomNavigation(config);

        bottomNavigation.getStylesheets().add(
                getClass().getResource("/com/Group3/foodorderingsystem/Module/Common/BottomNavCSS.css")
                        .toExternalForm());
        bottomNavigation.getStyleClass().add("bottom-navigation");
        return bottomNavigation;
    }

    private void handleNavigation(VendorNavigationEnum selectedComponent) {
        VendorViewModel.setSelectedNavigation(selectedComponent);
        layout.setBottom(buildBottomNavigator());
        layout.setTop(buildHeader());
    }

}
