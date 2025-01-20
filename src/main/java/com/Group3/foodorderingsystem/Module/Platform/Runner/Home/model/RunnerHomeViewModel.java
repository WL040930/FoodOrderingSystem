package com.Group3.foodorderingsystem.Module.Platform.Runner.Home.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Home.ui.RunnerHomeUI;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Home.ui.RunnerViewDetailsUI;

import javafx.scene.Node;

public class RunnerHomeViewModel extends ViewModelConfig {
    
    private RunnerHomeUI runnerHomeUI;
    private RunnerViewDetailsUI runnerViewDetailsUI;

    public void navigate(Node node) {
        setNode(node);

        RunnerViewModel.navigate(node);
    }

    public void init() {
        runnerHomeUI = new RunnerHomeUI();

        setNode(runnerHomeUI);

    }

    public RunnerHomeViewModel() {
        super();
        runnerHomeUI = new RunnerHomeUI();
    }


    public RunnerHomeUI getRunnerHomeUI() {
        return runnerHomeUI;
    }

    public void setRunnerHomeUI(RunnerHomeUI runnerHomeUI) {
        this.runnerHomeUI = runnerHomeUI;
    }

    public RunnerViewDetailsUI getRunnerViewDetailsUI() {
        return this.runnerViewDetailsUI;
    }

    public void setRunnerViewDetailsUI(RunnerViewDetailsUI runnerViewDetailsUI) {
        this.runnerViewDetailsUI = runnerViewDetailsUI;
    }

}
