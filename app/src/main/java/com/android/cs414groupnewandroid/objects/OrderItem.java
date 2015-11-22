package com.android.cs414groupnewandroid.objects;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;

/**
 * Created by darkbobo on 10/27/15.
 */
public class OrderItem {
    double price;
    int orderID;
    PIZZA_STATUS status;
	int itemID;
    @JsonIgnore
	Special special;

    public OrderItem(){

    }
    public OrderItem(double price){
        this.price = price;
        this.status = PIZZA_STATUS.NEW;
    }
    public OrderItem(double price, int orderID){
        this.price = price;
        this.orderID = orderID;
        this.status = PIZZA_STATUS.NEW;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public PIZZA_STATUS getStatus(){
        return status;
    }

    public void setStatus(PIZZA_STATUS status){
        this.status = status;
    }

    public void sendSideToMakeline(){
        if(this.status == PIZZA_STATUS.NEW) {
            this.status = PIZZA_STATUS.MAKELINE;
        }
    }

    public void sendPizzaToMakeline(){
        if(this.status == PIZZA_STATUS.NEW) {
            this.status = PIZZA_STATUS.MAKELINE;
        }
    }

	public void setItemID(int itemID){
		this.itemID = itemID;
	}

	public int getItemID(){
		return itemID;
	}

	public void calculatePrice(){

	}

	public boolean setSpecial(ArrayList<Special> specials){
		return false;
	}
}
