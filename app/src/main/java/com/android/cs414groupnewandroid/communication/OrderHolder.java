package com.android.cs414groupnewandroid.communication;

import com.android.cs414groupnewandroid.objects.Order;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Jim on 11/22/2015.
 */
@XStreamAlias("ORDERHOLDER")
public class OrderHolder {

    Order order;

    public OrderHolder(Order o) {
        this.order = o;
    }
}
