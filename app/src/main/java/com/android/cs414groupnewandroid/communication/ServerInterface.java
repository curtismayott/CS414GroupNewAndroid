package com.android.cs414groupnewandroid.communication;


import com.android.cs414groupnewandroid.objects.Order;
import com.android.cs414groupnewandroid.objects.Person;

/**
 * Created by Jim on 11/13/2015.
 */
public interface ServerInterface{

    //adds new order to PizzaSystem
    public int addOrder(Order order);

    //adds new customer to PizzaSystem
    public void addNewCustomer(Person customer);

    //saves edited customer profile
    public void saveCustomerProfile(Person customer);

    public Order getOrder(int OrderID);

}
