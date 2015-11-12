package com.android.cs414groupnewandroid.objects;

/**
 * Created by darkbobo on 10/5/15.
 */
public class Employee extends Person {
    private int userID;
    private String username;
    private String authentication;
    private ROLE role;
    public Employee(){
        super();
    }
    public Employee(String name, Address address, Phone phone, String username, String authentication, ROLE role) {
        super(name,address,phone);
        this.username = username;
        this.authentication = authentication;
        this.role = role;
    }

    @Override
    public String toString(){
        return getUserID() + " " + getName() + " " + role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthentication() { return authentication; }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public void setRole(ROLE role){
        this.role = role;
    }

    public ROLE getRole(){
        return role;
    }
}
