package com.Group3.foodorderingsystem.Module.Platform.Vendor.Review.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Review.ui.ReviewUI;

import javafx.scene.Node;

public class ReviewViewModel extends ViewModelConfig {

    @Override
    public void navigate(Node node) {
        setNode(node);
        VendorViewModel.navigate(getNode());
    }

    public ReviewViewModel() {
        super();
    }

    public void init() {
        Object vendor = SessionUtil.getVendorFromSession();
        if (vendor instanceof User) {
            setReviewUI(new ReviewUI(((User) vendor).getId()));
        } else {
            setReviewUI(new ReviewUI());
        }

        setNode(getReviewUI());
    }

    private ReviewUI reviewUI;

    public ReviewUI getReviewUI() {
        return reviewUI;
    }

    private void setReviewUI(ReviewUI reviewUI) {
        this.reviewUI = reviewUI;
    }
}
