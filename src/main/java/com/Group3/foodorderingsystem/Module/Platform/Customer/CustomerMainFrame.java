package com.Group3.foodorderingsystem.Module.Platform.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.BottomNavigationClass;
import com.Group3.foodorderingsystem.Core.Model.Entity.Config.HeaderClass;
import com.Group3.foodorderingsystem.Core.Widgets.BaseMainApplication;
import com.Group3.foodorderingsystem.Core.Widgets.BottomNavigation;
import com.Group3.foodorderingsystem.Core.Widgets.Header;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Assets.CustomerNavigationEnum;

import javafx.scene.Node;

public class CustomerMainFrame extends BaseMainApplication {
    
    @Override
    protected String sceneHeader() {
        return "Customer Panel";
    }

    @Override
    protected Node defaultContent() {
        return CustomerViewModel.getHomeViewModel().getNode(); 
    }

    @Override
    protected Header buildHeader() {
        CustomerNavigationEnum navigationList = CustomerViewModel.getSelectedNavigation();
        Header header = new Header(
                navigationList.getTopNavigationItem().stream()
                        .map(e -> new HeaderClass(
                                e.getTitle(),
                                CustomerViewModel.getSelectedTopNavigation() == e,
                                () -> {
                                    CustomerViewModel.setSelectedNavigation(e);
                                    updateHeader();
                                    e.getAction().run();
                                }))
                        .collect(Collectors.toList()));

        return header;
    }

    @Override
    protected BottomNavigation buildBottomNavigator() {
        List<BottomNavigationClass> config = new ArrayList<>();

        for (CustomerNavigationEnum component : CustomerViewModel.getNavigationList()) {
            config.add(new BottomNavigationClass(
                    component.getTitle(),
                    component.getIcon(),
                    () -> handleNavigation(component),
                    CustomerViewModel.getSelectedNavigation() == component));
        }

        BottomNavigation bottomNavigation = new BottomNavigation(config);
        bottomNavigation.setStyle(
                "-fx-background-color: #4CAF50; -fx-padding: 10px; -fx-min-height: 80px; -fx-max-height: 80px;");
        return bottomNavigation;
    }

    private void handleNavigation(CustomerNavigationEnum selectedComponent) {
        CustomerViewModel.setSelectedNavigation(selectedComponent);
        layout.setBottom(buildBottomNavigator());
        layout.setTop(buildHeader());
    }

}
