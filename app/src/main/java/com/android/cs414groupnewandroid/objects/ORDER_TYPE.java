package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by darkbobo on 11/9/15.
 */
@XStreamAlias("ORDERTYPE")
public enum ORDER_TYPE {
    CARRY_OUT,
    PICK_UP,
    DELIVERY
}
