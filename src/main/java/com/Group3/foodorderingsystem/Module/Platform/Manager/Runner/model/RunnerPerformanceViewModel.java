package com.Group3.foodorderingsystem.Module.Platform.Manager.Runner.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Runner.ui.RunnerListUI;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Runner.ui.RunnerRating;

import javafx.scene.Node;

public class RunnerPerformanceViewModel extends ViewModelConfig {

    @Override
    public void navigate(Node node) {
        setNode(node);
        ManagerViewModel.navigate(node);
    }

    public RunnerPerformanceViewModel() {
        super();

        runnerListUI = new RunnerListUI();
        setNode(runnerListUI);
    }

    private RunnerListUI runnerListUI;

    public RunnerListUI getRunnerListUI() {
        return runnerListUI;
    }

    public void setRunnerListUI(RunnerListUI runnerListUI) {
        this.runnerListUI = runnerListUI;
    }

    private RunnerRating runnerRating;

    public RunnerRating getRunnerRating() {
        return runnerRating;
    }

    public void setRunnerRating(RunnerRating runnerRating) {
        this.runnerRating = runnerRating;
    }
}
