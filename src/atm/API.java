package atm;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.json.simple.JSONObject;


public class API {

    public static CheckingAccount getCheckingAccount() {
        CheckingAccount acc = new CheckingAccount();
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject jsonObject = (JSONObject)obj;

            JSONObject holder = (JSONObject) jsonObject.get("checkingAccount");
            acc.setBalance((long)holder.get("balance"));
            acc.setAmountWithdrawn(((long)holder.get("amountWithdrawn")));
            acc.setAmountDepo(((long)holder.get("amountDepo")));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return acc;
    }

    public static void setCheckingAccount(CheckingAccount acc) {
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject database = (JSONObject)obj;

            JSONObject writeData = new JSONObject();
            writeData.put("balance", acc.getBalance());
            writeData.put("amountWithdrawn", acc.getAmountWithdrawn());
            writeData.put("amountDepo", acc.getAmountDepo());

            database.put("checkingAccount", writeData);

            try (FileWriter file = new FileWriter("./src/atm/database.json")) {
                file.write(database.toJSONString());
                file.flush();
            } catch(Exception e) {
                e.printStackTrace();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static SavingsAccount getSavingsAccount() {
        SavingsAccount acc = new SavingsAccount();
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject jsonObject = (JSONObject)obj;

            JSONObject holder = (JSONObject) jsonObject.get("savingsAccount");
            acc.setBalance((long)holder.get("balance"));
            acc.setAmountWithdrawn(((long)holder.get("amountWithdrawn")));
            acc.setAmountDepo(((long)holder.get("amountDepo")));
            acc.setAmountTransferred((long)holder.get("amountTransfer"));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return acc;
    }

    public static void setSavingsAccount(SavingsAccount acc) {
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject database = (JSONObject)obj;

            JSONObject writeData = new JSONObject();
            writeData.put("balance", acc.getBalance());
            writeData.put("amountWithdrawn", acc.getAmountWithdrawn());
            writeData.put("amountDepo", acc.getAmountDepo());
            writeData.put("amountTransfer", acc.getAmountTransferred());

            database.put("savingsAccount", writeData);

            try (FileWriter file = new FileWriter("./src/atm/database.json")) {
                file.write(database.toJSONString());
                file.flush();
            } catch(Exception e) {
                e.printStackTrace();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static UtilityAccount getUtilityAccount() {
        UtilityAccount acc = new UtilityAccount();
        return acc;
    }

    public static long getDayNum() {
        long dayNum = 0;
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject jsonObject = (JSONObject)obj;
            dayNum = (long)jsonObject.get("dayNum");
        } catch(Exception e) {
            e.printStackTrace();
        }

        return dayNum;
    }

    public static void setDayNum(long newDay) {
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject database = (JSONObject)obj;

            JSONObject writeData = new JSONObject();
            database.put("dayNum", newDay);

            try (FileWriter file = new FileWriter("./src/atm/database.json")) {
                file.write(database.toJSONString());
                file.flush();
            } catch(Exception e) {
                e.printStackTrace();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Bill[] getBills() {
        Bill[] bills = new Bill[4];
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject jsonObject = (JSONObject)obj;

            JSONArray holder = (JSONArray) jsonObject.get("bills");
            for (int i = 0; i < holder.size(); i++) {
                JSONObject temp = (JSONObject)holder.get(i);
                Bill tempBill = new Bill();
                tempBill.setPayAmount((long)temp.get("payAmount"));
                tempBill.setPayDate((long)temp.get("payDate"));
                tempBill.setPayStatus((boolean)temp.get("payStatus"));

                bills[i] = tempBill;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return bills;
    }

    public static void setBills(Bill[] bills) {
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject database = (JSONObject)obj;

            database.put("bills", bills);

            try (FileWriter file = new FileWriter("./src/atm/database.json")) {
                file.write(database.toJSONString());
                file.flush();
            } catch(Exception e) {
                e.printStackTrace();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
