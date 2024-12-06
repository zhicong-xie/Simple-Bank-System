//package cn.gtgz.config;
//
//import cn.gtgz.base.Base;
//
//import java.io.*;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class BankAccount1 extends Base{
//
//    public BankAccount1(){
//        super();
//    }
//
//    public static void getBankAccountsData() {
//        System.out.println(String.format("All bank account data : " + getTxtDataToMap()));
//    }
//
//    public static void createBankAccount(String accountName) {
//        inputDataToTxt(accountName, true);
//    }
//
//    public static void updateBankBalance(String accountName, String balance) {
//        updateDataToTxt(accountName, stringToDouble(balance));
//    }
//
//    public static void accountDeposits(String accountName, String money) {
//        deposit(accountName, money);
//    }
//
//    public static void accountWithdrawals(String accountName, String money) {
//        isWithdrawalsSuccess(accountName, money);
//    }
//
//    public static void viewAccountBalance(String accountName){
//        getAccountBalance(accountName);
//    }
//
//    public static void isAccountNameExisting(String accountName){
//        if (isDataExisting(accountName)){
//            System.out.println(String.format("%s account is already exists",accountName));
//        }else {
//            System.out.println(String.format("%s account does not exist",accountName));
//        }
//    }
//
//    public static void accountTransferToOther(String senderName, String receiveName, String transferAmonut){
//        if (isDataExisting(senderName)){
//            if (isDataExisting(receiveName)){
//                if (isWithdrawalsSuccess(senderName,transferAmonut)){
//                    accountDeposits(receiveName,transferAmonut);
//                }else {
//                    System.out.println("Transaction Cancellation");
//                }
//
//            }else {
//                System.out.println("receive account does not exist");
//            }
//        }else {
//            System.out.println("sender account does not exist");
//        }
//    }
//
//}
