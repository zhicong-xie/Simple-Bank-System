package cn.gtgz.base;


import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base {

    private static final String txtPath = "src/test/resources/BankAccountData.txt";
    private static final String amountDataRegex = "^(\\+)?\\d+(\\.?\\d+)?$";
    private static final String bankAccountRegex = "^[a-zA-Z0-9]+$";
    protected static LinkedHashMap<String,LinkedHashMap<String,String>> bankAccountDataInfo;

    //Constructor. Make sure bankAccountsData has data
    public Base() {
        if (bankAccountDataInfo == null){
            bankAccountDataInfo = getTxtStringToMap();
        }
    }

    public static BigDecimal stringToBigDecimal(String data) {
        return new BigDecimal(data).setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean isNameCompliance(String name){
        Pattern pattern = Pattern.compile(bankAccountRegex);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()){
            System.out.println("The name is not Compliance");
        }
        return matcher.matches();
    }

    public static boolean isAmountCompliance(String amount){
        Pattern pattern = Pattern.compile(amountDataRegex);
        Matcher matcher = pattern.matcher(amount);
        if (!matcher.matches()){
            System.out.println("The amount is not Compliance");
        }
        return matcher.matches();
    }

    public static LinkedHashMap<String,LinkedHashMap<String,String>> getTxtStringToMap(){

        try (FileInputStream fileInputStream = new FileInputStream(txtPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder stringBuilder = new StringBuilder();
                String stringBuild =  stringBuilder.append(line).toString();
                String num = stringBuild.split("-")[0];



                String[] keyValuePairs = stringBuild.split("-")[1].split(",");
                LinkedHashMap<String, String> bankAccountData = new LinkedHashMap<>();
                for (String pair : keyValuePairs) {
                    String[] entry = pair.split(":");
                    bankAccountData.put(entry[0], entry[1]);
                }
                bankAccountDataInfo.put(num,bankAccountData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bankAccountDataInfo;
    }

    public static void writeMapDataToTxt(LinkedHashMap<String,LinkedHashMap<String,String>> data,boolean isLastStartWriting){

        try {
            File file = new File(txtPath);
            FileOutputStream os = new FileOutputStream(file,isLastStartWriting);

            for (Map.Entry<String, LinkedHashMap<String, String>> entry : data.entrySet()) {
                String key = entry.getKey();
                Map<String, String> innerMap = entry.getValue();
                StringBuilder line =  new StringBuilder();
                for (Map.Entry<String, String> innerEntry : innerMap.entrySet()) {
                    String innerKey = innerEntry.getKey();
                    String innerValue = innerEntry.getValue();
                    line.append(innerKey + ":" + innerValue+",");
                }
                line =  line.insert(0,key+"-");
                line.setCharAt(line.length()-1,'\n');
                os.write(line.toString().getBytes());
            }

            os.close();
            System.out.println("The target file has been updated successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





//    //Read the target path txt data and convert it into Map storage
//    public static LinkedHashMap<String, Double> getTxtDataToMap() {
//
//        try (FileInputStream fileInputStream = new FileInputStream(bankAccountPath);
//             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] keyValue = line.split(",");
//                if (keyValue.length == 2) {
//                    bankAccountsData.put(keyValue[0].trim(), stringToDouble(keyValue[1].trim()));
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return bankAccountsData;
//    }
//
//    //Insert or overwrite data to the target txt
//    public static void inputDataToTxt(String accountName, boolean isNotOverwriteTxtFile) {
//        Pattern pattern = Pattern.compile(bankAccountRegex);
//        Matcher matcher = pattern.matcher(accountName);
//        if (matcher.matches()) {
//            if (!bankAccountsData.containsKey(accountName)) {
//                try (FileOutputStream fos = new FileOutputStream(bankAccountPath, isNotOverwriteTxtFile)) {
//                    String line = accountName + "," + defaultBalance + "\n";
//                    fos.write(line.getBytes());
//                    System.out.println(String.format("Create %s account success", accountName));
//                    bankAccountsData = getTxtDataToMap();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println(String.format("Creating %s account failed because the account already exists", accountName));
//            }
//        }else {
//            System.out.println("The Bank name you entered is invalid. Please enter any letters and numbers.");
//        }
//
//
//
//    }
//
//    //Change an existing account's balance
//    public static void updateDataToTxt(String accountName, double balance) {
//
//        if (bankAccountsData.containsKey(accountName)) {
//            bankAccountsData.put(accountName, balance);
//
//            try (FileOutputStream fos = new FileOutputStream(bankAccountPath)) {
//                for (String key : bankAccountsData.keySet()) {
//                    String line = key + "," + bankAccountsData.get(key).toString() + "\n";
//                    fos.write(line.getBytes());
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            bankAccountsData = getTxtDataToMap();
//
//        } else {
//            System.out.println(String.format("Update %s account balance fail because the account does not exist", accountName));
//        }
//    }
//
//    //Get the data of existing AccountName
//    public static double getAccountBalance(String accountName) {
//        double balance = stringToDouble(defaultBalance);
//        if (bankAccountsData.containsKey(accountName)) {
//            balance = bankAccountsData.get(accountName);
//            System.out.println(String.format("%s account current balance : %s", accountName, balance));
//        } else {
//            System.out.println(String.format("Get %s account balance fail because the account does not exist", accountName));
//        }
//        return balance;
//    }
//
//    //Account deposit
//    public static void deposit(String accountName, String depositBalance) {
//        double balanceBeforeDeposit = getAccountBalance(accountName);
//        System.out.println("Deposit money : " + depositBalance);
//        updateDataToTxt(accountName, balanceBeforeDeposit + stringToDouble(depositBalance));
//        bankAccountsData = getTxtDataToMap();
//        getAccountBalance(accountName);
//    }
//
//    //Account withdrawal(If the balance is less than the withdrawal amount, return false)
//    public static boolean isWithdrawalsSuccess(String accountName, String withdrawalsBalance) {
//        double balanceBeforeWithdrawals = getAccountBalance(accountName);
//
//        if (stringToDouble(withdrawalsBalance)!=null){
//            if (balanceBeforeWithdrawals >= stringToDouble(withdrawalsBalance)) {
//                updateDataToTxt(accountName, balanceBeforeWithdrawals + stringToDouble(withdrawalsBalance));
//                System.out.println("Withdrawals money : " + withdrawalsBalance);
//
//                updateDataToTxt(accountName, balanceBeforeWithdrawals - stringToDouble(withdrawalsBalance));
//                System.out.println("Withdrawals money success.");
//                bankAccountsData = getTxtDataToMap();
//                getAccountBalance(accountName);
//                return true;
//            } else {
//                System.out.println("Withdrawal amount insufficient balance.");
//            }
//        }
//        return false;
//
//    }
//
//    //
//    public static boolean isDataExisting(String data) {
//        return bankAccountsData.containsKey(data);
//    }

}
