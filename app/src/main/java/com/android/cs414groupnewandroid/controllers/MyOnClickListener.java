package com.android.cs414groupnewandroid.controllers;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.android.cs414groupnewandroid.communication.GetToppingsController;
import com.android.cs414groupnewandroid.objects.Register;

import java.util.HashMap;

/**
 * Created by darkbobo on 10/25/15.
 */
public class MyOnClickListener implements View.OnClickListener, AdapterView.OnItemClickListener {
    public int orderID;
    Register model;
    public GetToppingsController pc;
    HashMap<String, View> components;
	public Context context;
    View view;

	public MyOnClickListener(Context context){
		this.context = context;
        model = new Register();
	}

    public void setOrderID(int orderID){
        this.orderID = orderID;
    }


    public void addClient(GetToppingsController pc){
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

    public void addModel(Register model) {
        this.model = model;
    }
}
