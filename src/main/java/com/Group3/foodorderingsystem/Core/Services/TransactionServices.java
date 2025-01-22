package com.Group3.foodorderingsystem.Core.Services;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel.TransactionType;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class TransactionServices {

    private static List<TransactionModel> getTransaction() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.TRANSACTION), TransactionModel.class);
    }

    public static TransactionModel createTransaction(Double amount, TransactionModel.TransactionType transactionType,
            String userId) {
        if (transactionType != TransactionType.TOPUP && transactionType != TransactionType.WITHDRAWAL) {
            return null;
        }

        User user = UserServices.findUserById(userId);

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransactionId(Storage.generateNewId());
        transactionModel.setAmount(transactionType == TransactionType.TOPUP ? amount : -amount);
        transactionModel.setTransactionType(transactionType);
        transactionModel.setUserModel(user);

        switch (user.getRole()) {
            // Customer can only topup
            case CUSTOMER:
                CustomerModel customerModel = UserServices.findCustomerById(user.getId());
                customerModel.setBalance(customerModel.getBalance() + transactionModel.getAmount());
                NotificationServices.createNewNotification(customerModel.getId(),
                        NotificationServices.Template.receiveCredit(amount, transactionType));

                if (UserServices.saveUser(customerModel) == null) {
                    return null;
                }
                break;

            // vendor can only withdraw
            case VENDOR:
                VendorModel vendorModel = UserServices.findVendorById(user.getId());
                if (vendorModel.getRevenue() < amount) {
                    NotificationServices.createNewNotification(vendorModel.getId(),
                            NotificationServices.Template.transactionReject(amount, transactionType));
                    return null;
                }
                vendorModel.setRevenue(vendorModel.getRevenue() + transactionModel.getAmount());
                NotificationServices.createNewNotification(vendorModel.getId(),
                        NotificationServices.Template.withdrawCredit(amount, transactionType));
                if (UserServices.saveUser(vendorModel) == null) {
                    return null;
                }

                break;

            case RUNNER:
                RunnerModel runnerModel = UserServices.findRunnerById(user.getId());
                if (runnerModel.getRevenue() < amount) {
                    return null;
                }

                runnerModel.setRevenue(runnerModel.getRevenue() + transactionModel.getAmount());

                if (UserServices.saveUser(runnerModel) == null) {
                    return null;
                }

                break;

            default:
                break;
        }

        List<TransactionModel> transaction = getTransaction();
        transaction.add(transactionModel);

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.TRANSACTION), transaction);
        return transactionModel;
    }

    public static TransactionModel createTransaction(String orderId, TransactionModel.TransactionType transactionType,
            RoleEnum role) {
        if (transactionType != TransactionType.REFUND && transactionType != TransactionType.PAYMENT) {
            return null;
        }

        // WAIT FOR GETORDERBYID
        OrderModel orderModel = VendorOrderServices.getOrderById(orderId);
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransactionId(Storage.generateNewId());
        transactionModel.setAmount(orderModel.getTotalPrice());
        transactionModel.setTransactionType(transactionType);
        transactionModel.setOrderModel(orderModel);

        VendorModel vendorModel = UserServices.findVendorById(orderModel.getVendor());
        CustomerModel customerModel = UserServices.findCustomerById(orderModel.getCustomer());
        RunnerModel runnerModel = UserServices.findRunnerById(orderModel.getRider());

        switch (transactionType) {
            case PAYMENT:

                if (customerModel.getBalance() < orderModel.getTotalPrice()) {
                    return null;
                }

                switch (role) {
                    case CUSTOMER:
                        customerModel.setBalance(customerModel.getBalance() - orderModel.getTotalPrice());
                        NotificationServices.createNewNotification(customerModel.getId(),
                                NotificationServices.Template.payOrder(orderModel.getTotalPrice(),
                                        orderModel.getOrderId()));
                        break;
                    case VENDOR:
                        vendorModel.setRevenue(vendorModel.getRevenue() + orderModel.getSubTotalPrice());
                        NotificationServices.createNewNotification(vendorModel.getId(), NotificationServices.Template
                                .receiveOrderPayment(orderModel.getSubTotalPrice(), orderModel.getOrderId()));
                        break;
                    case RUNNER:
                        runnerModel.setRevenue(runnerModel.getRevenue() + orderModel.getDeliveryFee());
                        NotificationServices.createNewNotification(runnerModel.getId(), NotificationServices.Template
                                .receiveDeliveryPayment(orderModel.getDeliveryFee(), orderModel.getOrderId()));

                        break;
                    default:
                        break;
                }

                if (UserServices.saveUser(vendorModel) == null || UserServices.saveUser(customerModel) == null
                        || UserServices.saveUser(runnerModel) == null) {
                    return null;
                }

                break;

            case REFUND:
                VendorModel vendorModelRefund = UserServices.findVendorById(orderModel.getVendor());
                CustomerModel customerModelRefund = UserServices.findCustomerById(orderModel.getCustomer());

                vendorModelRefund.setRevenue(vendorModelRefund.getRevenue() - orderModel.getTotalPrice());
                customerModelRefund.setBalance(customerModelRefund.getBalance() + orderModel.getTotalPrice());

                if (UserServices.saveUser(vendorModelRefund) == null
                        || UserServices.saveUser(customerModelRefund) == null) {
                    return null;
                }

                break;
            default:
                break;
        }

        List<TransactionModel> transaction = getTransaction();
        transaction.add(transactionModel);

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.TRANSACTION), transaction);
        return transactionModel;
    }

    public static List<TransactionModel> getTransactionByUserId(String userId) {
        List<TransactionModel> transactions = getTransaction();
        List<TransactionModel> transactionList = new ArrayList<>();

        for (TransactionModel transaction : transactions) {
            if (transaction.getUserModel() != null) {
                if (transaction.getUserModel().getId().equals(userId)) {
                    transactionList.add(transaction);
                }
            } else if (transaction.getOrderModel() != null) {
                if (transaction.getOrderModel().getCustomer().equals(userId)) {
                    if (transaction.getTransactionType() == TransactionType.REFUND) {
                        transaction.setAmount(transaction.getAmount());
                        transactionList.add(transaction);
                    } else if (transaction.getTransactionType() == TransactionType.PAYMENT) {
                        transaction.setAmount(-transaction.getAmount());
                        transactionList.add(transaction);
                    }
                } else if (transaction.getOrderModel().getVendor().equals(userId)) {
                    if (transaction.getTransactionType() == TransactionType.REFUND) {
                        transaction.setAmount(-transaction.getAmount());
                        transactionList.add(transaction);
                    } else if (transaction.getTransactionType() == TransactionType.PAYMENT) {
                        transaction.setAmount(transaction.getAmount());
                        transactionList.add(transaction);
                    }
                }
            }
        }

        transactionList.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));

        return transactionList;
    }

    public static List<TransactionModel> getTransactions(String userId, LocalDate date, String month, Integer year) {
        // Retrieve all transactions for the user
        List<TransactionModel> transactions = getTransactionByUserId(userId);

        transactions = transactions.stream()
                .filter(transaction -> transaction.getTransactionType() != TransactionModel.TransactionType.WITHDRAWAL)
                .collect(Collectors.toList());

        // Filter by date
        if (date != null) {
            return transactions.stream()
                    .filter(transaction -> {
                        LocalDate transactionDate = convertToLocalDate(transaction.getTransactionDate());
                        return transactionDate.isEqual(date);
                    })
                    .collect(Collectors.toList());
        } else if (month != null && year != null) {
            int monthValue = Month.valueOf(month.toUpperCase()).getValue();
            return transactions.stream()
                    .filter(transaction -> {
                        LocalDate transactionDate = convertToLocalDate(transaction.getTransactionDate());
                        return transactionDate.getMonthValue() == monthValue && transactionDate.getYear() == year;
                    })
                    .collect(Collectors.toList());
        }
        // Filter by year
        else if (year != null) {
            return transactions.stream()
                    .filter(transaction -> {
                        LocalDate transactionDate = convertToLocalDate(transaction.getTransactionDate());
                        return transactionDate.getYear() == year;
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // Utility method to convert Date to LocalDate
    private static LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}