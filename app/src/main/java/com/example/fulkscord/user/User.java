package com.example.fulkscord.user;

public class User {

    private String username, email, phoneNumber, password;


    public User(String username, String email, String phoneNumber, String password){
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object other){
        if(!(other instanceof User)) return false;
        User u = (User) other;
        if(username.equals(u.getUsername()) && username.equals(u.getEmail())) return true;
        return false;
    }

    public int hashCode(){
        return email.hashCode(); //not username since multiple users can have same username
    }

}
