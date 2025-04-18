package com.Group3.foodorderingsystem.Module.Platform.Admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.BottomNavigationClass;
import com.Group3.foodorderingsystem.Core.Model.Entity.Config.HeaderClass;
import com.Group3.foodorderingsystem.Core.Widgets.BaseMainApplication;
import com.Group3.foodorderingsystem.Core.Widgets.BottomNavigation;
import com.Group3.foodorderingsystem.Core.Widgets.Header;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Assets.AdminNavigationEnum;

import javafx.scene.Node;

public class AdminMainFrame extends BaseMainApplication {

    @Override
    protected String sceneHeader() {
        return "Admin Panel";
    }

    @Override
    protected Node defaultContent() {
        return AdminViewModel.getDatabaseViewModel().getNode();
    }

    @Override
    protected Header buildHeader() {
        AdminNavigationEnum navigationList = AdminViewModel.getSelectedNavigation();
        Header header = new Header(
                navigationList.getTopNavigationItem().stream()
                        .map(e -> new HeaderClass(
                                e.getTitle(),
                                AdminViewModel.getSelectedTopNavigation() == e,
                                () -> {
                                    AdminViewModel.setSelectedNavigation(e);
                                    updateHeader();
                                    e.getAction().run();
                                }))
                        .collect(Collectors.toList()));

        return header;
    }

    @Override
    protected BottomNavigation buildBottomNavigator() {
        List<BottomNavigationClass> config = new ArrayList<>();

        for (AdminNavigationEnum component : AdminViewModel.getNavigationList()) {
            config.add(new BottomNavigationClass(
                    component.getTitle(),
                    component.getIcon(),
                    () -> handleNavigation(component),
                    AdminViewModel.getSelectedNavigation() == component));
        }

        BottomNavigation bottomNavigation = new BottomNavigation(config);

        bottomNavigation.getStylesheets().add(
                getClass().getResource("/com/Group3/foodorderingsystem/Module/Common/BottomNavCSS.css")
                        .toExternalForm());
        bottomNavigation.getStyleClass().add("bottom-navigation");
        return bottomNavigation;
    }

    private void handleNavigation(AdminNavigationEnum selectedComponent) {
        AdminViewModel.setSelectedNavigation(selectedComponent);
        layout.setBottom(buildBottomNavigator());
        layout.setTop(buildHeader());
    }
}
