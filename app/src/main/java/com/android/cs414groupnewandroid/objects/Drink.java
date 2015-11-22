package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by darkbobo on 10/26/15.
 */
@XStreamAlias("DRINK")
public class Drink extends SideItem {
    public Drink(){
        super();
    }
    public Drink(String name, double price){
        super(name, price);
    }
}
