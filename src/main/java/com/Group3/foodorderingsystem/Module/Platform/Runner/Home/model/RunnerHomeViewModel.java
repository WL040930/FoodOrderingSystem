package com.Group3.foodorderingsystem.Module.Platform.Runner.Home.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Runner.Home.ui.RunnerHomeUI;

import javafx.scene.Node;

public class RunnerHomeViewModel extends ViewModelConfig {
    
    @Override
    public void navigate(Node node) {
        
    }

    public RunnerHomeViewModel() {
        super();
        runnerHomeUI = new RunnerHomeUI();
    }

    public void init() {
        setNode(runnerHomeUI);
    }

    private RunnerHomeUI runnerHomeUI;

    public RunnerHomeUI getRunnerHomeUI() {
        return runnerHomeUI;
    }

    public void setRunnerHomeUI(RunnerHomeUI runnerHomeUI) {
        this.runnerHomeUI = runnerHomeUI;
    }
}
