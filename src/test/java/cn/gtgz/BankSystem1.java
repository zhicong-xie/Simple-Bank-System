//package cn.gtgz.config;
//
//import java.util.Scanner;
//
//public class BankSystem1 {
//
//    private static BankAccount1 bankAccount1;
//
//    public BankSystem1(){
//        bankAccount1 = new BankAccount1();
//    }
//
//    public static void bankSystemMenu(){
//        Scanner scanner = new Scanner(System.in);
//        String operate = scanner.nextLine();
//
//        switch (operate){
//            case "1" :
//                bankAccount1.getBankAccountsData();
//                System.out.println("Bank account query completed.\nAutomatically return to the system menu page.");
//                backToBankSystemMenu();
//                break;
//            case "2" :
//                System.out.println("Please enter the name of the bank account you want to create.(Support input of any letters and numbers)");
//                String accountName = scanner.nextLine();
//                bankAccount1.createBankAccount(accountName);
//                backToBankSystemMenu();
//                break;
//            case "3" :
//                System.out.println("Please enter the existing Bank account name you want to query.");
//                accountName = scanner.nextLine();
//                bankAccount1.viewAccountBalance(accountName);
//                backToBankSystemMenu();
//                break;
//            case "4" :
//                System.out.println("Please enter the bank account name you want to deposit");
//                accountName = scanner.nextLine();
//                bankAccount1.viewAccountBalance(accountName);
//                System.out.println("Please enter the amount you want to deposit");
//                String amount = scanner.nextLine();
//                bankAccount1.accountDeposits(accountName,amount);
//                backToBankSystemMenu();
//                break;
//            case "5" :
//                System.out.println("Please enter the bank account name you want to withdraw");
//                accountName = scanner.nextLine();
//                bankAccount1.viewAccountBalance(accountName);
//                System.out.println("Please enter the amount you want to withdraw");
//                amount = scanner.nextLine();
//                bankAccount1.isWithdrawalsSuccess(accountName,amount);
//                backToBankSystemMenu();
//                break;
//            case "6" :
//                break;
//            case "7" :
//                System.out.println("Successfully exited the banking system.");
//                break;
//
//            default:
//                System.out.println("The option you entered is invalid and you have exited the banking system.");
//
//
//
//        }
//    }
//
//    public static void backToBankSystemMenu(){
//        System.out.println("\n------------------------------ Back to Bank System Menu. Please enter any option from 1 to 7.   ------------------------------");
//        bankSystemMenu();
//    }
//
//    public static void startBankSystemMenu() {
//
//        System.out.println("--------------------------      Welcome to the banking system     ----------------------------------\n");
//        System.out.println("Please select the module you want to go to :\n1. Get your existing bank account\n2. Create a bank account\n3. View your account balance\n4. Deposit\n5. Withdraw\n6. Transfer\n7. Exit\n");
//        System.out.println("Please enter any option from 1 to 7");
//        bankSystemMenu();
//    }
//
//
//    public static void main(String[] args) {
//        BankAccount1 bankAccount1 = new BankAccount1();
//        startBankSystemMenu();
//    }
//
//}
