package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by clark on 10/7/15.
 */
@XStreamAlias("PHONE")
public class Phone {
    @XStreamAlias("number")
    private String number;

    public Phone(){

    }

    @Override
    public String toString(){
        return number;
    }

    public Phone(String number) { this.number = number; }

    public String getNumber() { return number; }

    public void setNumber(String number) { /*check if valid number*/this.number = number;}

    @Override
    public boolean equals(Object o) {
        if (o instanceof Phone) {
            if (o.toString().equals(this.toString())) {
                return true;
            }
        }
        return false;
    }
}
