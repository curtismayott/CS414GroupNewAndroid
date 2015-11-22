package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by darkbobo on 9/28/15.
 */
@XStreamAlias("TOPPING")
public class Topping {
    @XStreamAlias("itemid")
    int itemID;
    @XStreamAlias("shortname")
    String shortName;
    @XStreamAlias("fullname")
    String fullName;

    //default for JSON
    public Topping(){}
    public Topping(String shortName){
        this.shortName = shortName;
    }
    public Topping(String shortName, String fullName){
        this.shortName = shortName;
        this.fullName = fullName;
    }

    @Override
    public String toString(){
        return fullName;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Topping)){
            return false;
        }else{
            Topping t = (Topping)o;
            if(getFullName().equals(t.getFullName())){
                return true;
            }else{
                return false;
            }
        }
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
