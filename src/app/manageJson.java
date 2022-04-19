package app;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonObject;

public class manageJson {   
    
    public static void creatingJsonDoc(){
        JsonObject jsonObject = new JsonObject();
        
        try{
            FileWriter file = new FileWriter("./blockchain.json");
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public static void writeJson(String data) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Blocs :", data);

        try{
            FileWriter file = new FileWriter("./blockchain.json", true);
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
