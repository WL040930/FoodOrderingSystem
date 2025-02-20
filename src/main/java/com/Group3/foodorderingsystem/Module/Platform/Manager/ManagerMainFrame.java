package com.Group3.foodorderingsystem.Module.Platform.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.BottomNavigationClass;
import com.Group3.foodorderingsystem.Core.Model.Entity.Config.HeaderClass;
import com.Group3.foodorderingsystem.Core.Widgets.BaseMainApplication;
import com.Group3.foodorderingsystem.Core.Widgets.BottomNavigation;
import com.Group3.foodorderingsystem.Core.Widgets.Header;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Assets.ManagerNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Assets.RunnerNavigationEnum;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class ManagerMainFrame extends BaseMainApplication {

    @Override
    protected String sceneHeader() {
        return "Manager Panel";
    }

    @Override
    protected Node defaultContent() {
        return ManagerViewModel.getComplainViewModel().getNode();
    }

    @Override
    protected Header buildHeader() {
        ManagerNavigationEnum navigationList = ManagerViewModel.getSelectedNavigation();
        Header header = new Header(
                navigationList.getTopNavigationItem().stream()
                        .map(e -> new HeaderClass(
                                e.getTitle(),
                                ManagerViewModel.getSelectedTopNavigation() == e,
                                () -> {
                                    ManagerViewModel.setSelectedNavigation(e);
                                    updateHeader();
                                    e.getAction().run();
                                }))
                        .collect(Collectors.toList()));

        return header;
    }

    @Override
    protected BottomNavigation buildBottomNavigator() {
        List<BottomNavigationClass> config = new ArrayList<>();

        for (ManagerNavigationEnum component : ManagerViewModel.getNavigationList()) {
            config.add(new BottomNavigationClass(
                    component.getTitle(),
                    component.getIcon(),
                    () -> handleNavigation(component),
                    ManagerViewModel.getSelectedNavigation() == component));
        }

        BottomNavigation bottomNavigation = new BottomNavigation(config);

        bottomNavigation.getStylesheets().add(
                getClass().getResource("/com/Group3/foodorderingsystem/Module/Common/BottomNavCSS.css")
                        .toExternalForm());
        bottomNavigation.getStyleClass().add("bottom-navigation");
        return bottomNavigation;
    }

    private void handleNavigation(ManagerNavigationEnum selectedComponent) {
        ManagerViewModel.setSelectedNavigation(selectedComponent);
        layout.setBottom(buildBottomNavigator());
        layout.setTop(buildHeader());
    }

}
