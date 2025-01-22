package com.Group3.foodorderingsystem.Module.Platform.Vendor.Review.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
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
        setReviewUI(new ReviewUI());
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
