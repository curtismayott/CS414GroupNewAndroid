package com.android.cs414groupnewandroid.objects;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Tyler on 10/27/15.
 */
@XStreamAlias("SIDESTATUS")
public enum SIDE_STATUS {
    NEW,
    MAKELINE,
    LOADED,
    COMPLETED
}
