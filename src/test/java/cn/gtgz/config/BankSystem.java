package cn.gtgz.config;

import java.util.Scanner;

public class BankSystem {

  private BankAccountProfile bankAccountProfile;
  Scanner scanner = new Scanner(System.in);

  public BankSystem() {
    bankAccountProfile = new BankAccountProfile();
  }

  public void bankSystemMenu() {
    System.out.println(
        "\n--------------------------      Welcome to the banking system     ----------------------------------\n");
    String operate1;
    String operate2;

    Loop1:
    while (true) {
      System.out.println(
          "Please select the module you want to go to :\n1. Login your bank account\n2. Create a bank account\n3. Exit\n");
      System.out.println("Please enter any option from 1 to 3");
      operate1 = scanner.nextLine();

      switch (operate1) {
        case "1":
          bankAccountProfile.loginFunction();
          if (bankAccountProfile.getLoginVerification()) {

            System.out.println(
                "--------------------------      Login to the banking account successful     ----------------------------------\n");

            Loop2:
            while (true) {
              System.out.println(
                  "Please select the module you want to go to :\n1. View account details\n2. Account deposit\n3. Account withdrawals\n4. Transfer to other accounts\n5. Exist\n6. Return to previous page");
              System.out.println("Please enter any option from 1 to 6");
              operate2 = scanner.nextLine();
              switch (operate2) {
                case "1":
                  bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                  break;
                case "2":
                  bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                  System.out.println("Please enter the amount you want to deposit");
                  String amount = scanner.nextLine();
                  bankAccountProfile.accountDeposit(amount);
                  bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                  System.out.println(
                      String.format(
                          "%s account successfully transferred %s amount",
                          bankAccountProfile.getLoggedAccountNum(), amount));
                  break;
                case "3":
                  bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                  System.out.println("Please enter the amount you want to withdrawals");
                  amount = scanner.nextLine();
                  bankAccountProfile.accountWithdrawals(amount);
                  bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                  break;
                case "4":
                  bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                  System.out.println("Please enter the amount you want to transfer amount");
                  amount = scanner.nextLine();
                  System.out.println("Please enter the receive number");
                  String receiveNum = scanner.nextLine();
                  bankAccountProfile.accountTransferToOther(receiveNum, amount);
                  break;
                case "5":
                  System.out.println("Successfully exited the banking system.");
                  break Loop1;
                case "6":
                  System.out.println("Successfully return to previous page");
                  bankAccountProfile.logoffProfile();
                  break Loop2;
                default:
                  System.out.println(
                      "The option you entered is invalid and you have exited the banking system.\n");
              }
            }
          }
          break;
        case "2":
          System.out.println("Please input your account name");
          String accountName = scanner.nextLine();
          System.out.println("Please input your account password");
          String password = scanner.nextLine();
          bankAccountProfile.createAccountAndUpdateToTxt(accountName, password);
          System.out.println(
              "--------------------------      Login to the banking account successful     ----------------------------------\n");

          Loop2:
          while (true) {
            System.out.println(
                "Please select the module you want to go to :\n1. View account details\n2. Account deposit\n3. Account withdrawals\n4. Transfer to other accounts\n5. Exist\n6. Return to previous page");
            System.out.println("Please enter any option from 1 to 5");
            operate2 = scanner.nextLine();
            switch (operate2) {
              case "1":
                bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                break;
              case "2":
                bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                System.out.println("Please enter the amount you want to deposit");
                String amount = scanner.nextLine();
                bankAccountProfile.accountDeposit(amount);
                bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                System.out.println(
                    String.format(
                        "%s account successfully transferred %s amount",
                        bankAccountProfile.getLoggedAccountNum(), amount));
                break;
              case "3":
                bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                System.out.println("Please enter the amount you want to withdrawals");
                amount = scanner.nextLine();
                bankAccountProfile.accountWithdrawals(amount);
                bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                break;
              case "4":
                bankAccountProfile.viewAccountDetails(bankAccountProfile.getLoggedAccountNum());
                System.out.println("Please enter the amount you want to transfer amount");
                amount = scanner.nextLine();
                System.out.println("Please enter the receive number");
                String receiveNum = scanner.nextLine();
                bankAccountProfile.accountTransferToOther(receiveNum, amount);
                break;
              case "5":
                System.out.println("Successfully exited the banking system.");
                break Loop1;
              case "6":
                System.out.println("Successfully return to previous page");
                bankAccountProfile.logoffProfile();
                break Loop2;
              default:
                System.out.println(
                    "The option you entered is invalid and you have exited the banking system.\n");
            }
          }
          break;
        case "3":
          System.out.println("Successfully exited the banking system.");
          break Loop1;
        default:
          System.out.println(
              "The option you entered is invalid and you have exited the banking system.\n");
      }
    }
  }

  public static void main(String[] args) {
    BankSystem bankSystem = new BankSystem();
    bankSystem.bankSystemMenu();
  }
}
