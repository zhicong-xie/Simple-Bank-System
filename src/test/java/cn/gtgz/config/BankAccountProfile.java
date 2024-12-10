package cn.gtgz.config;

import cn.gtgz.base.Base;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Scanner;

public class BankAccountProfile extends Base {

  protected String isLoginVerification = "false";
  private String defaultBalance = "0.00";
  protected String loggedAccountNum = "";
  protected String accountNum;

  public boolean isAccountNumExist(String accountNum) {
    return bankAccountDataInfo.containsKey(accountNum);
  }

  public String generateRandomNumbers() {
    Random random = new Random();
    String randomNumberString = "";
    int tryTime = 0;
    do {
      long randomNumber =
          random.longs(1000000000000000L, 10000000000000000L).findFirst().getAsLong();
      randomNumberString = String.valueOf(randomNumber);
    } while (isAccountNumExist(randomNumberString));
    return randomNumberString;
  }

  public void createAccountAndUpdateToTxt(String name, String psw) {
    LinkedHashMap<String, String> newAccountInfo = new LinkedHashMap<>();
    String accountNum = generateRandomNumbers();
    newAccountInfo.put("bankAccountName", name);
    newAccountInfo.put("bankAccountBalance", defaultBalance);
    newAccountInfo.put("bankAccountPsw", psw);
    newAccountInfo.put("state", "active");
    bankAccountDataInfo.put(accountNum, newAccountInfo);
    writeMapDataToTxt(bankAccountDataInfo, false);
    isLoginVerification = accountNum + "true";
    loggedAccountNum = accountNum;
    viewAccountDetails(accountNum);
  }

  public void updateAccountAndUpdateToTxt(String accountNum, BigDecimal balance) {
    LinkedHashMap<String, LinkedHashMap<String, String>> data = bankAccountDataInfo;
    LinkedHashMap<String, String> getAccountInfo = data.get(accountNum);
    getAccountInfo.put("bankAccountBalance", balance.toString());
    data.put(accountNum, getAccountInfo);
    writeMapDataToTxt(data, false);
  }

  public LinkedHashMap<String, LinkedHashMap<String, String>> updateAccountAndUpdateToMap(
      String accountNum) {
    LinkedHashMap<String, LinkedHashMap<String, String>> allData = bankAccountDataInfo;
    LinkedHashMap<String, String> getAccountInfo = allData.get(accountNum);
    getAccountInfo.put("state", "non");
    allData.put(accountNum, getAccountInfo);
    return allData;
  }

  public String getAccountPassword(String accountNum) {
    return bankAccountDataInfo.get(accountNum).get("bankAccountPsw");
  }

  public boolean isAccountActive(String accountNum) {
    return bankAccountDataInfo.get(accountNum).get("state").equals("active");
  }

  public void loginFunction() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please input your bank account number");
    accountNum = scanner.nextLine();
    if (isAccountNumExist(accountNum)) {
      if (isAccountActive(accountNum)) {
        System.out.println("Please input your bank account password");
        String expectPsw = getAccountPassword(accountNum);
        int tryOpportunity = 2;
        do {
          String psw = scanner.nextLine();
          if (psw.equals(expectPsw)) {
            System.out.println(
                "Password correct, successfully logged in. will automatically log out of the last logged in account.\n");
            isLoginVerification = accountNum + "true";
            loggedAccountNum = accountNum;
            break;
          } else {
            if (tryOpportunity != 0) {
              System.out.println(
                  String.format("Password error, There are still %s chances left", tryOpportunity));
              tryOpportunity--;
            } else {

              writeMapDataToTxt(updateAccountAndUpdateToMap(accountNum), false);
              System.out.println("Your account has been locked, please contact xxx-xxx-xxxxx\n");
              tryOpportunity--;
            }
          }
        } while (tryOpportunity >= 0);
      } else {
        System.out.println("Your account has been locked, please contact xxx-xxx-xxxxx\n");
      }
      //
    } else {
      System.out.println("Your account does not exist\n");
    }
  }

  public void viewAccountDetails(String accountNum) {
    if (isAccountNumExist(accountNum)) {
      if (isLoginVerification.contains(accountNum + "true")) {
        LinkedHashMap<String, LinkedHashMap<String, String>> data = bankAccountDataInfo;
        for (String key : data.get(accountNum).keySet()) {
          if (!key.equals("bankAccountPsw")) {
            System.out.println(String.format(key + " : " + data.get(accountNum).get(key)) + "\n");
          }
        }
      } else {
        System.out.println("Please login your profile first\n");
      }
    } else {
      System.out.println("Your account does not exist\n");
    }
  }

  public String getAccountBalance(String accountNum) {
    if (isAccountNumExist(accountNum)) {
      if (isLoginVerification.contains(accountNum + "true")) {
        LinkedHashMap<String, LinkedHashMap<String, String>> data = bankAccountDataInfo;
        for (String key : data.get(accountNum).keySet()) {
          if (key.equals("bankAccountBalance")) {
            return data.get(accountNum).get("bankAccountBalance");
          }
        }
      } else {
        System.out.println("Please login your profile first\n");
      }
    } else {
      System.out.println("Your account does not exist\n");
    }
    return "";
  }

  public void accountDeposit(String transactionAmount) {
    if (isLoginVerification.equals(loggedAccountNum + "true")) {

      if (isAmountCompliance(transactionAmount)) {
        updateAccountAndUpdateToTxt(
            loggedAccountNum,
            stringToBigDecimal(getAccountBalance(loggedAccountNum))
                .add(stringToBigDecimal(transactionAmount)));
      }
    } else {
      System.out.println("Please login your account first\n");
    }
  }

  public void accountWithdrawals(String transactionAmount) {
    if (isLoginVerification.equals(loggedAccountNum + "true")) {

      if (isAmountCompliance(transactionAmount)) {
        if (stringToBigDecimal(getAccountBalance(loggedAccountNum))
                .compareTo(stringToBigDecimal(transactionAmount))
            >= 0) {
          updateAccountAndUpdateToTxt(
              loggedAccountNum,
              stringToBigDecimal(getAccountBalance(loggedAccountNum))
                  .subtract(stringToBigDecimal(transactionAmount)));
          System.out.println("Withdrawal successful\n");
        } else {
          System.out.println("Withdrawal fail, The transfer amount exceeds the balance\n");
        }
      } else {
        System.out.println("Please login your account first\n");
      }
    }
  }

  public void accountTransferToOther(String receiveNum, String transferAmount) {

    if (isAccountNumExist(receiveNum)) {
      LinkedHashMap<String, LinkedHashMap<String, String>> allData = bankAccountDataInfo;
      LinkedHashMap<String, String> sendData = allData.get(loggedAccountNum);
      LinkedHashMap<String, String> receiveData = allData.get(receiveNum);

      BigDecimal senderBeforeAmount = new BigDecimal(sendData.get("bankAccountBalance"));
      BigDecimal receiveBeforeAmount = new BigDecimal(receiveData.get("bankAccountBalance"));
      BigDecimal amount = new BigDecimal(transferAmount);
      BigDecimal senderAfterAmount;
      BigDecimal receiveAfterAmount;

      if (senderBeforeAmount.compareTo(amount) >= 0) {
        senderAfterAmount = senderBeforeAmount.subtract(amount);
        receiveAfterAmount = receiveBeforeAmount.add(amount);
        sendData.put("bankAccountBalance", senderAfterAmount.toString());
        receiveData.put("bankAccountBalance", receiveAfterAmount.toString());
        allData.put(loggedAccountNum, sendData);
        allData.put(receiveNum, receiveData);
        writeMapDataToTxt(allData, false);
        allData = bankAccountDataInfo;
        System.out.println(
            "Send account transfer was successful, the balance is : "
                + allData.get(loggedAccountNum).get("bankAccountBalance")
                + "\n");
      } else {
        System.out.println("The transfer amount exceeds the balance\n");
      }
    } else {
      System.out.println("Receive account does not exist\n");
    }
  }

  public boolean getLoginVerification() {
    return isLoginVerification.contains(loggedAccountNum + "true")
        && isLoginVerification.contains(accountNum);
  }

  public String getLoggedAccountNum() {
    return loggedAccountNum;
  }

  public void logoffProfile() {
    isLoginVerification = "false";
    loggedAccountNum = "";
  }
}
