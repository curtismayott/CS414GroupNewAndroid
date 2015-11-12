package com.android.cs414groupnewandroid.controllers;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.android.cs414groupnewandroid.objects.Register;

import java.util.HashMap;

/**
 * Created by darkbobo on 10/25/15.
 */
public class MyOnClickListener implements View.OnClickListener, AdapterView.OnItemClickListener {
    public int orderID;
    public Register model;
    HashMap<String, View> components;
	Context context;
    //WindowManager manager;
    View view;

	public MyOnClickListener(Context context){
		this.context = context;
	}

    public void setOrderID(int orderID){
        this.orderID = orderID;
    }

    public void addModel(Register model){
        this.model = model;
    }

    public void addView(View view){
        this.view = view;
    }

    /*public void addWindowManager(WindowManager manager){
        this.manager = manager;
    }*/

    public void registerComponent(String labelID, View component){
        components.put(labelID, component);
    }

    public int getOrderID(){
        return orderID;
    }

    public void resetView(){

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
