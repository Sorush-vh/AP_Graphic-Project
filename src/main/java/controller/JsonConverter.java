package controller;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.User;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class JsonConverter {
    public static void putUserDataInFile(User user,String username,String dirFromSrc) throws ParseException {

        Gson gson= new Gson();
        String userInJsonString=gson.toJson(user);
        removeUserDataByUsername(username, dirFromSrc);

        JSONArray userData= null;
        try {
            userData = getUsersDataInJson(dirFromSrc);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        
        userData.add(userInJsonString);

            try{
                File file=new File(dirFromSrc);
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(userData.toJSONString());  
                fileWriter.flush();  
                fileWriter.close();  
            } catch ( IOException e) {  
                e.printStackTrace();  
            }
    }

    public static void removeUsernameJsonData(String username, String password, String dirFromSrc) throws ParseException{
        JSONArray userData=getUsersDataInJson(dirFromSrc);
        int index=getUserIndexInJsonArray(username, password);
        userData.remove(index);
        try{
            File file=new File(dirFromSrc);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(userData.toJSONString());  
            fileWriter.flush();  
            fileWriter.close();  
        } catch ( IOException e) {  
            e.printStackTrace();  
        }
    }

    public static void fillFormerUsersDatabase(String dirFromSrc) throws ParseException {
        JSONArray usersJsonArray=getUsersDataInJson(dirFromSrc);

        Gson gson=new Gson();
        for (int i = 0; i < usersJsonArray.size(); i++) {
            String UserInJson= usersJsonArray.get(i).toString();
            User userUnderRestoration=gson.fromJson(UserInJson, User.class);
            User.addUser(userUnderRestoration);
        }
    }

    private static JSONArray getUsersDataInJson(String dirFromSrc) throws org.json.simple.parser.ParseException {
        JSONArray formerData=new JSONArray();
        try {  
            
            JSONParser jsonParser = new JSONParser();
            Object objjj = jsonParser.parse(new FileReader(dirFromSrc));
            formerData=(JSONArray) objjj;

        } catch (  IOException e) {
            e.printStackTrace();  
        }
        return formerData;
    }

    private static int getUserIndexInJsonArray(String username, String dirFromSrc)  {
        JSONArray jsonDataArray=null;
        try {
            jsonDataArray = getUsersDataInJson(dirFromSrc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User jsonUser;
        String jsonUserInString;
        Gson gson= new Gson();
        for (int i = 0; i < jsonDataArray.size(); i++) {
            jsonUserInString= jsonDataArray.get(i).toString();
            jsonUser = gson.fromJson(jsonUserInString, User.class);
            if(jsonUser.getUsername().equals(username))
                return i;
        }
        return -1;
    }

    public static void removeUserDataByUsername(String username,String dirFromSrc){
        JSONArray jsonDataArray=null;
        try {
            jsonDataArray = getUsersDataInJson(dirFromSrc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User jsonUser;
        String jsonUserInString;
        Gson gson= new Gson();
        for (int i = 0; i < jsonDataArray.size(); i++) {
            jsonUserInString= jsonDataArray.get(i).toString();
            jsonUser = gson.fromJson(jsonUserInString, User.class);
            if(jsonUser.getUsername().equals(username)){
                jsonDataArray.remove(i);
                break;
            }
        }
        try{
            File file=new File(dirFromSrc);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonDataArray.toJSONString());  
            fileWriter.flush();  
            fileWriter.close();  
        } catch ( IOException e) {  
            e.printStackTrace();  
        }
    }
}

