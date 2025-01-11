package com.Group3.foodorderingsystem.Module.Platform.Admin.Requests.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Requests.ui.RequestsUI;

import javafx.scene.Node;

public class RequestsViewModel extends ViewModelConfig {

    @Override
    public void navigate(Node node) {

    }

    public RequestsViewModel() {
        super();
    }

    public void init() {
        setRequestsUI(new RequestsUI());
        setNode(getRequestsUI());
    }

    private RequestsUI requestsUI;

    public RequestsUI getRequestsUI() {
        return requestsUI;
    }

    public void setRequestsUI(RequestsUI requestsUI) {
        this.requestsUI = requestsUI;
    }
}
