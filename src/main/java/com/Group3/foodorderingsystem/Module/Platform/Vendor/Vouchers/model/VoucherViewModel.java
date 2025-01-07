package com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.ui.VoucherAddUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.ui.VoucherEditUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.ui.VoucherListUI;

import javafx.scene.Node;

public class VoucherViewModel extends ViewModelConfig{

    @Override
    public void navigate(Node node) {
        setNode(node);
        VendorViewModel.navigate(node);
    }

    public VoucherViewModel() {
        super();
        this.voucherListUI = new VoucherListUI();
    }

    public void init() {
        setNode(voucherListUI);
    }

    private VoucherListUI voucherListUI;

    public VoucherListUI getVoucherListUI() {
        return voucherListUI;
    }

    public void setVoucherListUI(VoucherListUI voucherListUI) {
        this.voucherListUI = voucherListUI;
    }

    private VoucherAddUI voucherAddUI;

    public VoucherAddUI getVoucherAddUI() {
        return voucherAddUI;
    }

    public void setVoucherAddUI(VoucherAddUI voucherAddUI) {
        this.voucherAddUI = voucherAddUI;
    }
    
    private VoucherEditUI voucherEditUI;

    public VoucherEditUI getVoucherEditUI() {
        return voucherEditUI;
    }

    public void setVoucherEditUI(VoucherEditUI voucherEditUI) {
        this.voucherEditUI = voucherEditUI;
    }
}
