package com.Group3.foodorderingsystem.Module.Common.Transaction.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Common.Transaction.ui.TopupWithdrawUI;
import com.Group3.foodorderingsystem.Module.Common.Transaction.ui.UserFinance;

import javafx.scene.Node;

public class TransactionViewModel extends ViewModelConfig {

    @Override
    public void navigate(Node node) {
        setNode(node);
    }

    public TransactionViewModel() {
        super();
    }

    public void init() {
        this.userFinance = new UserFinance();
        setNode(userFinance);
    }

    private UserFinance userFinance;

    public UserFinance getUserFinance() {
        return userFinance;
    }

    public void setUserFinance(UserFinance userFinance) {
        this.userFinance = userFinance;
    }

    private TopupWithdrawUI topupWithdrawUI;

    public TopupWithdrawUI getTopupWithdrawUI() {
        return topupWithdrawUI;
    }

    public void setTopupWithdrawUI(TopupWithdrawUI topupWithdrawUI) {
        this.topupWithdrawUI = topupWithdrawUI;
    }

}