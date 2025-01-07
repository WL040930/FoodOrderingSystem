package com.Group3.foodorderingsystem.Core.Services;

import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.VoucherModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class VoucherServices {

    private static List<VoucherModel> getVouchers() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.VOUCHER), VoucherModel.class);
    }

    public static VoucherModel saveVouchers(VoucherModel voucher) {
        List<VoucherModel> vouchers = getVouchers();
        for (VoucherModel v : vouchers) {
            if (v.getVoucherCode().equals(voucher.getVoucherCode())) {
                return null;
            }
        }
        vouchers.add(voucher);
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.VOUCHER), vouchers);
        return voucher;
    }

    public static List<VoucherModel> getVouchersByVendorId(String vendorId) {
        VendorModel vendor = UserServices.findVendorById(vendorId);
        List<VoucherModel> vouchers = new ArrayList<>();

        for (VoucherModel voucher : getVouchers()) {
            if (voucher.getVendor().getId().equals(vendor.getId())) {
                voucher.setVendor(vendor);
                vouchers.add(voucher);
            }
        }

        return vouchers;
    }

    public static VoucherModel updateVoucher(VoucherModel voucher) {
        List<VoucherModel> vouchers = getVouchers();
        for (int i = 0; i < vouchers.size(); i++) {
            if (vouchers.get(i).getVoucherCode().equals(voucher.getVoucherCode())) {
                vouchers.set(i, voucher); // Replace the voucher at index i with the new voucher
                FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.VOUCHER), vouchers); // Save the updated list to
                                                                                           // the file
                return voucher; // Return the updated voucher
            }
        }
        return null; // Return null if the voucher was not found
    }

    /**
     * @param voucherCode - Provide the voucher code
     * @param vendor      - order from which vendor
     * @return VoucherModel - return the voucher model
     * @return null - if no voucher found
     */
    public static VoucherModel getApplicableVoucher(String voucherCode, VendorModel vendor) {
        for (VoucherModel voucher : getVouchers()) {
            if (voucher.getVoucherCode().equals(voucherCode) && voucher.getVendor().getId().equals(vendor.getId())) {
                return voucher;
            }
        }
        return null;
    }

    public static boolean deleteVoucher(String voucherCode) {
        List<VoucherModel> vouchers = getVouchers();
        for (int i = 0; i < vouchers.size(); i++) {
            if (vouchers.get(i).getVoucherCode().equals(voucherCode)) {
                vouchers.remove(i); // Remove the voucher at index i
                FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.VOUCHER), vouchers); // Save the updated list to
                                                                                           // the file
                return true; // Return true if the voucher was deleted
            }
        }
        return false; // Return false if the voucher was not found
    }
}
