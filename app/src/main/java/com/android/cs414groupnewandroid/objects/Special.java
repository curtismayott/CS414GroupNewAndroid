package com.android.cs414groupnewandroid.objects;

import java.io.Serializable;

/**
 * Created by darkbobo on 11/17/15.
 */
public class Special implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    PizzaSize size;
    int numToppings;
    double discountedPrice;

    public Special(){

    }

    public Special(PizzaSize size, int numToppings, double discountedPrice) {
        this.size = size;
        this.numToppings = numToppings;
        this.discountedPrice = discountedPrice;
    }

    public PizzaSize getSize() {
        return size;
    }

    public void setSize(PizzaSize size) {
        this.size = size;
    }

    public int getNumToppings() {
        return numToppings;
    }

    public void setNumToppings(int numToppings) {
        this.numToppings = numToppings;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
