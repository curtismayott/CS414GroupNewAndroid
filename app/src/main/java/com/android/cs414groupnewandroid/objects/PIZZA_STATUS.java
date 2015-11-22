package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by darkbobo on 10/5/15.
 */
@XStreamAlias("PIZZASTATUS")
public enum PIZZA_STATUS {
    NEW,
    MAKELINE,
    LOADED,
    COMPLETED
}
