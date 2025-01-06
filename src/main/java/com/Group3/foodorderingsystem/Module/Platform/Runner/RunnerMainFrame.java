package com.Group3.foodorderingsystem.Module.Platform.Runner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.BottomNavigationClass;
import com.Group3.foodorderingsystem.Core.Model.Entity.Config.HeaderClass;
import com.Group3.foodorderingsystem.Core.Widgets.BaseMainApplication;
import com.Group3.foodorderingsystem.Core.Widgets.BottomNavigation;
import com.Group3.foodorderingsystem.Core.Widgets.Header;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Assets.RunnerNavigationEnum;

import javafx.scene.Node;

public class RunnerMainFrame extends BaseMainApplication {

    @Override
    protected String sceneHeader() {
        return "Runner Panel";
    }

    @Override
    protected Node defaultContent() {
        return RunnerViewModel.getHomeViewModel().getNode();
    }

    @Override
    protected Header buildHeader() {
        RunnerNavigationEnum navigationList = RunnerViewModel.getSelectedNavigation();
        Header header = new Header(
                navigationList.getTopNavigationItem().stream()
                        .map(e -> new HeaderClass(
                                e.getTitle(),
                                RunnerViewModel.getSelectedTopNavigation() == e,
                                () -> {
                                    RunnerViewModel.setSelectedNavigation(e);
                                    updateHeader();
                                    e.getAction().run();
                                }))
                        .collect(Collectors.toList()));

        return header;
    }

    @Override
    protected BottomNavigation buildBottomNavigator() {
        List<BottomNavigationClass> config = new ArrayList<>();

        for (RunnerNavigationEnum component : RunnerViewModel.getNavigationList()) {
            config.add(new BottomNavigationClass(
                    component.getTitle(),
                    component.getIcon(),
                    () -> handleNavigation(component),
                    RunnerViewModel.getSelectedNavigation() == component));
        }

        BottomNavigation bottomNavigation = new BottomNavigation(config);

        bottomNavigation.getStylesheets().add(
                getClass().getResource("/com/Group3/foodorderingsystem/Module/Common/BottomNavCSS.css")
                        .toExternalForm());
        bottomNavigation.getStyleClass().add("bottom-navigation");
        return bottomNavigation;
    }

    private void handleNavigation(RunnerNavigationEnum selectedComponent) {
        RunnerViewModel.setSelectedNavigation(selectedComponent);
        layout.setBottom(buildBottomNavigator());
        layout.setTop(buildHeader());
    }

}
