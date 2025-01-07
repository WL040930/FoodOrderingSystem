package com.Group3.foodorderingsystem.Core.Model.Entity.Order;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VoucherModel {

    private String voucherCode;
    private double discountRate;
    private VendorModel vendor;

    public VoucherModel() {
    }

    public VoucherModel(String voucherCode, double discountRate, VendorModel vendor) {
        this.voucherCode = voucherCode;
        this.discountRate = discountRate;
        this.vendor = vendor;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public VendorModel getVendor() {
        return vendor;
    }

    public void setVendor(VendorModel vendor) {
        this.vendor = vendor;
    }
}
