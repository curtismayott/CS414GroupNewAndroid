package com.android.cs414groupnewandroid.controllers;

import android.content.Context;
import android.view.View;

import com.android.cs414groupnewandroid.activities.MainActivity;

import java.util.HashMap;

/**
 * Created by darkbobo on 10/25/15.
 */
public class MainMenuListener extends MyOnClickListener {
    public MainMenuListener(Context context){
		super(context);
        components = new HashMap<>();
    }

    @Override
    public void onClick(View v){
		if(v.equals(components.get("newOrder"))){

			MainActivity.changeScreen(MainActivity.CUSTOMER);
		}
    }
}
