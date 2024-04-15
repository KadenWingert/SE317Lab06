package atm;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class UtilityAccount {
    private static long lastID = 123455;
    private long accountNumber;
    private String username, password;

    UtilityAccount(){
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject jsonObject = (JSONObject)obj;

            JSONObject holder = (JSONObject) jsonObject.get("utilityAccount");
            accountNumber = (long)holder.get("accNum");
            username = (String)holder.get("username");
            password = (String)holder.get("password");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    UtilityAccount(String username, String password){
        this.username = username;
        this.password = password;
        accountNumber = ++lastID;
    }

    void setAccount(){
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/atm/database.json"));
            JSONObject database = (JSONObject)obj;

            JSONObject writeData = new JSONObject();
            writeData.put("accNum", accountNumber);
            writeData.put("username", username);
            writeData.put("password", password);

            database.put("utilityAccount", writeData);

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

    boolean checkUsername(String username){
        return username.equals(this.username);
    }

    boolean checkPassword(String password){
        return password.equals(this.password);
    }

    boolean checkAccountNum(int num){
        return num == accountNumber;
    }

    long getAccountNumber(){
        return accountNumber;
    }
}
