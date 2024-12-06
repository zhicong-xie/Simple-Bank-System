package cn.gtgz.config;


import cn.gtgz.base.Base;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class BankAccountProfile extends Base {

    private static boolean isLoginVerification = false;
    private static String defaultBalance = "0.00";
//    private Base base;

    public BankAccountProfile(){
        super();
    }


    public static boolean isAccountNumExist(String accountNum){
        return bankAccountDataInfo.containsKey(accountNum);
    }

    public static String generateRandomNumbers(){
        Random random = new Random();
        String randomNumberString = "";
        int tryTime = 0;
        do {
            long randomNumber = random.longs(1000000000000000L, 10000000000000000L).findFirst().getAsLong();
            randomNumberString = String.valueOf(randomNumber);
        }while (isAccountNumExist(randomNumberString));
        return randomNumberString;
    }


    public static LinkedHashMap<String,LinkedHashMap<String,String>> createAccountAndUpdateToMap(String name,String psw){
        LinkedHashMap<String,String> newAccountInfo = new LinkedHashMap<>();
        newAccountInfo.put("bankAccountName",name);
        newAccountInfo.put("bankAccountBalance",defaultBalance);
        newAccountInfo.put("bankAccountPsw",psw);
        newAccountInfo.put("state","active");
        bankAccountDataInfo.put(generateRandomNumbers(),newAccountInfo);
        return bankAccountDataInfo;
    }

    public static LinkedHashMap<String,LinkedHashMap<String,String>> UpdateAccountAndUpdateToMap(String accountNum ,String balance){
        LinkedHashMap<String,String> getAccountInfo = bankAccountDataInfo.get(accountNum);
        getAccountInfo.put("bankAccountBalance",balance);
        bankAccountDataInfo.put(accountNum,getAccountInfo);
        return bankAccountDataInfo;
    }

    public static LinkedHashMap<String,LinkedHashMap<String,String>> UpdateAccountAndUpdateToMap(String accountNum){
        LinkedHashMap<String,String> getAccountInfo = getTxtStringToMap().get(accountNum);
        getAccountInfo.put("state","non-active");
        bankAccountDataInfo.put(accountNum,getAccountInfo);
        return bankAccountDataInfo;
    }

    public static String getAccountPassword(String accountNum){
       return bankAccountDataInfo.get(accountNum).get("bankAccountPsw");
    }

    public static boolean isAccountActive(String accountNum){
        return bankAccountDataInfo.get(accountNum).get("state").equals("active");
    }

    public static void loginFunction(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input your bank account number");
        String accountNum = scanner.nextLine();
        if (isAccountNumExist(accountNum)){
            if (isAccountActive(accountNum)){
                isLoginVerification = false;
            System.out.println("Please input your bank account password");
            String expectPsw = getAccountPassword(accountNum);
            int tryOpportunity = 2;
            do {
                String psw = scanner.nextLine();
                if (psw.equals(expectPsw)){
                    System.out.println("Password correct, successfully logged in");
                    isLoginVerification = true;
                    break;
                }else {
                    if (tryOpportunity != 0){
                        System.out.println(String.format("Password error, There are still %s chances left",tryOpportunity));
                        tryOpportunity --;
                    }else {
                        writeMapDataToTxt(UpdateAccountAndUpdateToMap(accountNum),false);
                        System.out.println("Your account has been locked, please contact xxx-xxx-xxxxx");
                        tryOpportunity --;
                    }
                }
            }while (tryOpportunity>=0);
            }else {
                System.out.println("Your account has been locked, please contact xxx-xxx-xxxxx");
            }
//
        }else {
            System.out.println("Your account does not exist");
        }
    }

    public static void viewAccountDetails(String accountNum){
        if (isLoginVerification){
            for (String key : bankAccountDataInfo.get(accountNum).keySet()){
                if (!key.equals("bankAccountPsw")){
                    System.out.println(String.format(key + " : "+bankAccountDataInfo.get(key))+"\n");
                }
            }
        }else {
            System.out.println("Please login your profile first");
        }

    }


//    public static void main(String[] args) {
//        Syst

//        StringBuilder jsonString = new StringBuilder();
//        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\new\\Build a Simple Bank System\\src\\test\\resources\\aaa.txt");
//             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                jsonString.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONArray jsonArray = new JSONArray(jsonString.toString());
//        HashMap<String,HashMap<String,String>> allData = new HashMap<>();
//        for (Object ob :jsonArray ){
//            HashMap<String,String> accountData = new HashMap<>();
//            JSONObject jsonObject1 = (JSONObject) ob;
//            accountData.put("bankAccountName",jsonObject1.get("bankAccountName").toString());
//            accountData.put("bankAccountBalance",jsonObject1.get("bankAccountBalance").toString());
//            accountData.put("bankAccountPsw",jsonObject1.get("bankAccountPsw").toString());
//            accountData.put("state",jsonObject1.get("state").toString());
//            allData.put(jsonObject1.get("bankAccountNum").toString(),accountData);
//        }
//
//        System.out.println(allData);

//        LinkedHashMap<String,LinkedHashMap<String,String>> allBankAccountData = new LinkedHashMap<>();
//        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\new\\Build a Simple Bank System\\src\\test\\resources\\aaa.txt");
//             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                StringBuilder stringBuilder = new StringBuilder();
//                String stringBuild =  stringBuilder.append(line).toString();
//                String num = stringBuild.split("-")[0];
//
//
//
//                String[] keyValuePairs = stringBuild.split("-")[1].split(",");
//                LinkedHashMap<String, String> bankAccountData = new LinkedHashMap<>();
//                for (String pair : keyValuePairs) {
//                    String[] entry = pair.split(":");
//                    bankAccountData.put(entry[0], entry[1]);
//                }
//                allBankAccountData.put(num,bankAccountData);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(allBankAccountData);
//        Random random = new Random();
//        long randomNumber = random.longs(1000000000000000L, 10000000000000000L).findFirst().getAsLong();
//        String randomNumberString = String.valueOf(randomNumber);
//        System.out.println(randomNumberString);
//        System.out.println(randomNumberString.length());
//    }
//
//    public static void main(String[] args) {
//        getTxtStringToMap(txtPath);
//        writeMapDataToTxt(txtPath,UpdateAccountAndUpdateToMap(txtPath,"65432123456789","500.00"),false);
//        writeMapDataToTxt(txtPath,createAccountAndUpdateToMap("abc","098765"),false);
//    }
}





