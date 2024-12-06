package cn.gtgz.config;

import cn.gtgz.base.Base;

public class BankAccountTransaction extends Base {


    public BankAccountTransaction(){
        super();
    }


    public String transaction(String beforeBalance, String option, String transactionAmount){
        switch (option){
            case "deposits":
                if (isAmountCompliance(transactionAmount)){
                    return stringToBigDecimal(beforeBalance).add(stringToBigDecimal(transactionAmount)).toString();
                }
                break;
            case "withdrawals":
                if (isAmountCompliance(transactionAmount)){
                    if (stringToBigDecimal(beforeBalance).compareTo(stringToBigDecimal(transactionAmount))>=0){
                        return stringToBigDecimal(beforeBalance).subtract(stringToBigDecimal(transactionAmount)).toString();
                    }
                }
                break;
        }
        System.out.println("Transaction Cancellation");
        return beforeBalance;
    }
}
