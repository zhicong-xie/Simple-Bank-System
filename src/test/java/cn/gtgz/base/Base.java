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
    protected static LinkedHashMap<String, LinkedHashMap<String, String>> bankAccountDataInfo = new LinkedHashMap<>();

    public Base() {
        bankAccountDataInfo = getTxtStringToMap();
    }

    public static BigDecimal stringToBigDecimal(String data) {
        return new BigDecimal(data).setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean isNameCompliance(String name) {
        Pattern pattern = Pattern.compile(bankAccountRegex);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            System.out.println("The name is not Compliance");
        }
        return matcher.matches();
    }

    public static boolean isAmountCompliance(String amount) {
        Pattern pattern = Pattern.compile(amountDataRegex);
        Matcher matcher = pattern.matcher(amount);
        if (!matcher.matches()) {
            System.out.println("The amount is not Compliance");
        }
        return matcher.matches();
    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>> getTxtStringToMap() {
        try (FileInputStream fileInputStream = new FileInputStream(txtPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder stringBuilder = new StringBuilder();
                String stringBuild = stringBuilder.append(line).toString();
                String num = stringBuild.split("-")[0];


                String[] keyValuePairs = stringBuild.split("-")[1].split(",");
                LinkedHashMap<String, String> bankAccountData = new LinkedHashMap<>();
                for (String pair : keyValuePairs) {
                    String[] entry = pair.split(":");
                    bankAccountData.put(entry[0], entry[1]);
                }
                bankAccountDataInfo.put(num, bankAccountData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bankAccountDataInfo;
    }

    public static void writeMapDataToTxt(LinkedHashMap<String, LinkedHashMap<String, String>> data, boolean isLastStartWriting) {

        try {
            File file = new File(txtPath);
            FileOutputStream os = new FileOutputStream(file, isLastStartWriting);

            for (Map.Entry<String, LinkedHashMap<String, String>> entry : data.entrySet()) {
                String key = entry.getKey();
                Map<String, String> innerMap = entry.getValue();
                StringBuilder line = new StringBuilder();
                for (Map.Entry<String, String> innerEntry : innerMap.entrySet()) {
                    String innerKey = innerEntry.getKey();
                    String innerValue = innerEntry.getValue();
                    line.append(innerKey + ":" + innerValue + ",");
                }
                line = line.insert(0, key + "-");
                line.setCharAt(line.length() - 1, '\n');
                os.write(line.toString().getBytes());
            }

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
