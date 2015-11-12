package com.android.cs414groupnewandroid.objects;

import java.util.ArrayList;

/**
 * Created by clark on 10/7/15.
 */
public class Person {
    private String name;
    private ArrayList<Address> addresses;
    private ArrayList<Phone> phoneNumbers;

    public Person(){

    }
    public Person(String name, Address address, Phone phone) {
        this.addresses = new ArrayList<Address>();
        this.phoneNumbers = new ArrayList<Phone>();
        this.name = name;
        this.addresses.add(address);
        this.phoneNumbers.add(phone);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Address> getAddresses() {
        return this.addresses;
    }

    public ArrayList<Phone> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<Phone> phones){
        this.phoneNumbers = phones;
    }

    public Address getAddress(int index){
        return this.addresses.get(index);
    }

    public void addAddress(Address address) {
        if(this.addresses == null){
            this.addresses = new ArrayList<>();
        }
        this.addresses.add(address);
    }

    public void setAddresses(ArrayList<Address> addresses) {
        if(this.addresses == null){
            this.addresses = new ArrayList<>();
        }
        this.addresses = addresses;
    }

}
