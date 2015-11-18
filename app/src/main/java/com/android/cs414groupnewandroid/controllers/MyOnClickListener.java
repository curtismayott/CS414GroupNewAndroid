package com.android.cs414groupnewandroid.controllers;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import java.util.HashMap;

/**
 * Created by darkbobo on 10/25/15.
 */
public class MyOnClickListener implements View.OnClickListener, AdapterView.OnItemClickListener {
    public int orderID;
    public PizzaClient pc;
    HashMap<String, View> components;
	Context context;
    View view;

	public MyOnClickListener(Context context){
		this.context = context;
	}

    public void setOrderID(int orderID){
        this.orderID = orderID;
    }


    public void addClient(PizzaClient pc){
        this.pc = pc;
    }


    public void addView(View view){
        this.view = view;
    }


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
