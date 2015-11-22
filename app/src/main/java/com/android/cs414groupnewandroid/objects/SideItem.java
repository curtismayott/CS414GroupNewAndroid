package com.android.cs414groupnewandroid.objects;

import java.util.ArrayList;

/**
 * Created by darkbobo on 10/26/15.
 */
public class SideItem extends OrderItem {
    int itemID;
    String name;

    public SideItem(){
        super();
    }
    public SideItem(String name, double price){
        super(price);
        this.name = name;
    }

    @Override
    public String toString(){
        return getName() + "   " + getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

	@Override
	public void calculatePrice(){
		double basePrice = 0;
		if(special == null){
			basePrice += getPrice();
		}else{
			basePrice += special.getDiscountedPrice();
		}
		setPrice(basePrice);
	}
	@Override
	public boolean setSpecial(ArrayList<Special> specials) {
		double price = 1000;
		for(Special s : specials){
			if(s.getItemType().equals("Side") && s.getSideItem().equals(this)){
				if(price > s.getDiscountedPrice()) {
					this.special = s;
					price = s.getDiscountedPrice();
				}
			}
		}
		return false;
	}
}
