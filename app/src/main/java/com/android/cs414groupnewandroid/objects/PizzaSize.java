package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by darkbobo on 9/28/15.
 */
@XStreamAlias("PIZZASIZE")
public class PizzaSize extends Topping{
    @XStreamAlias("price")
    double price;

    //add for jackson
    public PizzaSize(){}
    public PizzaSize(String shortName, double price) {
        super(shortName);
        this.price = price;
    }

    public PizzaSize(String shortName, String fullName, double price) {
        super(shortName, fullName);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
