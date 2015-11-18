package com.android.cs414groupnewandroid.activities;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.android.cs414groupnewandroid.R;
import com.android.cs414groupnewandroid.controllers.CustomerListener;
import com.android.cs414groupnewandroid.controllers.MainMenuListener;
import com.android.cs414groupnewandroid.controllers.MyOnClickListener;
import com.android.cs414groupnewandroid.controllers.OrderEditListener;
import com.android.cs414groupnewandroid.controllers.PizzaServer;
import com.android.cs414groupnewandroid.fragments.*;
import com.android.cs414groupnewandroid.objects.Register;

import java.util.HashMap;

public class MainActivity extends FragmentActivity {
	private static HashMap<String,BaseFragment> fragments;
	private static HashMap<String,MyOnClickListener> listeners;
	private static FragmentManager manager;
	public static final String CUSTOMER = "customer";
	public static final String PIZZA = "pizza";
	public static final String ORDER_EDIT = "order_edit";
	public static final String MAIN_MENU = "main_menu";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragments = new HashMap<>();
		fragments.put(CUSTOMER, CustomerFragment.newInstance());
		fragments.put(PIZZA, PizzaFragment.newInstance());
		fragments.put(ORDER_EDIT, OrderFragment.newInstance());
		fragments.put(MAIN_MENU, MainActivityFragment.newInstance());

		listeners = new HashMap<>();
		listeners.put(CUSTOMER, new CustomerListener(this));
		listeners.put(PIZZA, new OrderEditListener(this));
		listeners.put(ORDER_EDIT, listeners.get(PIZZA));
		listeners.put(MAIN_MENU, new MainMenuListener(this));

		for(String key : fragments.keySet()){
			fragments.get(key).setController(listeners.get(key));
		}

		Register model = new Register();
		for(String key : listeners.keySet()){
			listeners.get(key).addModel(model);
		}

		manager = getSupportFragmentManager();
		manager.beginTransaction().add(R.id.container, fragments.get(MAIN_MENU)).commit();
		PizzaServer.getInstance();
	}

	public static void changeScreen(String newView){
		manager.beginTransaction().replace(R.id.container, fragments.get(newView)).commit();
		listeners.get(newView).resetView();
	}

	public static void passOrderID(String newView, int orderID){
		Message msg = new Message();
		msg.arg1 = orderID;
		msg.what = BaseFragment.PASS_ID;
		listeners.get(newView).setOrderID(orderID);
	}

	@Override
	public void onBackPressed(){

	}
}
