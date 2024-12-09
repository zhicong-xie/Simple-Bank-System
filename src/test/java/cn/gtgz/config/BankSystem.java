package cn.gtgz.config;


import java.util.Scanner;

import static cn.gtgz.config.BankAccountProfile.loggedAccountNum;
import static cn.gtgz.config.BankAccountProfile.isLoginVerification;


public class BankSystem {


    public static void bankSystemMenu() {
        Scanner scanner = new Scanner(System.in);
        String operate = scanner.nextLine();

        switch (operate) {
            case "1":
                BankAccountProfile.loginFunction();
                if (isLoginVerification.contains("true")) {
                    afterSuccessfulLogin();
                }
                break;
            case "2":
                System.out.println("Please input your account name");
                String accountName = scanner.nextLine();
                System.out.println("Please input your account password");
                String password = scanner.nextLine();
                BankAccountProfile.createAccountAndUpdateToTxt(accountName, password);
                afterSuccessfulLogin();
                break;
            case "3":
                System.out.println("Successfully exited the banking system.");
                break;
            default:
                System.out.println("The option you entered is invalid and you have exited the banking system.");


        }
    }

    public static void afterSuccessfulLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------------------      Login to the banking account successful     ----------------------------------\n");
        System.out.println("Please select the module you want to go to :\n1. View account details\n2. Account deposit\n3. Account withdrawals\n4.Transfer to other accounts\n5. Exist");
        String operate = scanner.nextLine();
        switch (operate) {
            case "1":
                BankAccountProfile.viewAccountDetails(loggedAccountNum);
                break;
            case "2":
                BankAccountProfile.viewAccountDetails(loggedAccountNum);
                System.out.println("Please enter the amount you want to deposit");
                String amount = scanner.nextLine();
                BankAccountProfile.accountDeposit(amount);
                BankAccountProfile.viewAccountDetails(loggedAccountNum);
                System.out.println(String.format("%s account successfully transferred %s amount", loggedAccountNum, amount));
                break;
            case "3":
                BankAccountProfile.viewAccountDetails(loggedAccountNum);
                System.out.println("Please enter the amount you want to withdrawals");
                amount = scanner.nextLine();
                BankAccountProfile.accountWithdrawals(amount);
                BankAccountProfile.viewAccountDetails(loggedAccountNum);
                break;
            case "4":
                BankAccountProfile.viewAccountDetails(loggedAccountNum);
                System.out.println("Please enter the amount you want to transfer amount");
                amount = scanner.nextLine();
                System.out.println("Please enter the receive number");
                String receiveNum = scanner.nextLine();
                BankAccountProfile.accountTransferToOther(receiveNum, amount);
                break;
            case "5":
                System.out.println("Successfully exited the banking system.");
                break;
            default:
                System.out.println("The option you entered is invalid and you have exited the banking system.");
        }
    }

    public static void startBankSystemMenu() {
        System.out.println("--------------------------      Welcome to the banking system     ----------------------------------\n");
        System.out.println("Please select the module you want to go to :\n1. Login your bank account\n2. Create a bank account\n3. Exit\n");
        System.out.println("Please enter any option from 1 to 4");
        bankSystemMenu();
    }


    public static void main(String[] args) {
        startBankSystemMenu();
    }

}
