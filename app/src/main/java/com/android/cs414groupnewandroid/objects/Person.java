package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;

/**
 * Created by clark on 10/7/15.
 */
@XStreamAlias("PERSON")
public class Person implements CustomerInterface {
    @XStreamAlias("name")
    private String name;
    @XStreamAlias("addresses")
    private ArrayList<Address> addresses;
    @XStreamAlias("phonenumbers")
    private ArrayList<Phone> phoneNumbers;
    @XStreamAlias("points")
    double points;

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

    public ArrayList<Phone> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public Address getAddress(int index){
        return this.addresses.get(index);
    }

	@Override
	public Person getCustomer() {
		return null;
	}

	@Override
	public void editCustomer(Person person) {

	}

	@Override
	public void addCustomer(Person person) {

	}

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
