package com.android.cs414groupnewandroid.objects;

/**
 * Created by darkbobo on 9/28/15.
 */
public class Address {
    String streetAddress;
    String city;
    String state;
    String zipcode;

    public Address(){

    }

    public Address(String streetAddress, String city, String state, String zipcode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public String toString() {
        return this.getStreetAddress() + " " + this.getCity() + " " + this.getState() + " " + this.getZipcode();
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public boolean equals(Object o) {
        if (o instanceof Address) {
            if (o.toString().equals(this.toString())) {
                return true;
            }
        }
        return false;
    }
}
