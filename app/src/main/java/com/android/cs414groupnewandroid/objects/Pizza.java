package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;

/**
 * Created by darkbobo on 9/28/15.
 */
@XStreamAlias("PIZZA")
public class Pizza extends OrderItem {
    @XStreamAlias("toppings")
    ArrayList<Topping> toppings;
    @XStreamAlias("sauce")
    Sauce sauce;
    @XStreamAlias("size")
    PizzaSize size;
    @XStreamAlias("price")
    double price;
    @XStreamAlias("orderid")
    int orderID;

    public Pizza(){
        toppings = new ArrayList<>();
    }

    public Pizza(ArrayList<Topping> toppings, Sauce sauce, PizzaSize size, PIZZA_STATUS status, double price) {
        this.toppings = toppings;
        this.sauce = sauce;
        this.size = size;
        this.status = status;
        this.price = price;
    }

    @Override
    public String toString(){
        return size.toString() + " " + getSauce() + " " + getToppingList() + " $" + getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pizza)){
            return false;
        }else {
            Pizza p = (Pizza)o;
            if (getSauce().equals(p.getSauce()) && getSize().equals(p.getSize()) && getStatus() == p.getStatus()) {
                for (Topping t : p.getToppingList()) {
                    if (!getToppingList().contains(t)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
            return true;
        }
    }

    public ArrayList<Topping> getToppingList() {
        return toppings;
    }

    public void setToppingList(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public PizzaSize getSize() {
        return size;
    }

    public void setSize(PizzaSize size) {
        this.size = size;
    }

    public void loadPizza(){
        this.status = PIZZA_STATUS.LOADED;
    }

    public void completePizza(){
        this.status = PIZZA_STATUS.COMPLETED;
    }

	@Override
    public void calculatePrice(){
        // should abstract out so pricing calculations are dynamic
        double basePrice = 0;
		if(special == null) {
			basePrice += size.getPrice();
			basePrice += toppings.size();
		}else{
			basePrice += special.getDiscountedPrice();
			if(toppings.size() > special.getDiscountedPrice()){
				basePrice += toppings.size() - special.getDiscountedPrice();
			}
		}
        setPrice(basePrice);
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @Override
    public int getOrderID() {
        return orderID;
    }

    public String makelineToString(){
        // System.out.println("OrderID" + orderID);
        return  size.toString() + " " + sauce + " " + toppings.toString();
    }
	@Override
	public boolean setSpecial(ArrayList<Special> specials){
		double price = 1000;
		for(Special s : specials) {
			if (s.getItemType().equals("Pizza") && getSize() == s.getSize()) {
				if(price > s.getDiscountedPrice()) {
					this.special = s;
					price = s.getDiscountedPrice();
				}
			}
		}
		return false;
	}
}
