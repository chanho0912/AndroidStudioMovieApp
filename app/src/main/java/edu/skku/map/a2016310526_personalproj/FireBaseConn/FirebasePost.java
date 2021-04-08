package edu.skku.map.a2016310526_personalproj.FireBaseConn;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {

    private String Username;
    private String Fullname;
    private String Password;
    private String Birthday;
    private String Email;

    public FirebasePost(){

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public FirebasePost(String username, String fullname, String password, String birthday, String e_mail) {
        Username = username;
        Fullname = fullname;
        Password = password;
        Birthday = birthday;
        Email = e_mail;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("Username", Username);
        result.put("Fullname", Fullname);
        result.put("Password", Password);
        result.put("Birthday", Birthday);
        result.put("Email", Email);

        return result;
    }
}
