package com.android.cs414groupnewandroid.controllers;


import com.android.cs414groupnewandroid.objects.*;

import java.util.ArrayList;

/**
 * Created by Jim on 11/13/2015.
 */
public interface ServerInterface{

    //test method to print off orders
    public String printOrders();

    //adds new order to PizzaSystem
    public int addOrder(Order order);

    //adds new customer to PizzaSystem
    public void addNewCustomer(Person customer);

    //saves edited customer profile
    public void saveCustomerProfile(Person customer);

    public Order getOrder(int OrderID);

    public ArrayList<Topping> getMenuToppings();

    public ArrayList<Sauce> getSauces();

    public ArrayList<PizzaSize> getSizes();

    public ArrayList<Side> getSides();

    public ArrayList<Drink> getDrinks();

    public void updateOrder(int orderID, Order order);

}
