package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by darkbobo on 9/28/15.
 */
@XStreamAlias("SAUCE")
public class Sauce extends Topping {
    public Sauce(){}
    public Sauce(String shortName) {
        super(shortName);
    }

    public Sauce(String shortName, String fullName) {
        super(shortName, fullName);
    }
}
