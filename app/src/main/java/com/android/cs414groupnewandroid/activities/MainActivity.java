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
import com.android.cs414groupnewandroid.controllers.ServerInterface;
import com.android.cs414groupnewandroid.fragments.BaseFragment;
import com.android.cs414groupnewandroid.fragments.CustomerFragment;
import com.android.cs414groupnewandroid.fragments.MainActivityFragment;
import com.android.cs414groupnewandroid.fragments.OrderFragment;
import com.android.cs414groupnewandroid.fragments.PizzaFragment;

import java.io.IOException;
import java.util.HashMap;

import lipermi.handler.CallHandler;
import lipermi.net.Client;

public class MainActivity extends FragmentActivity {

	private final String serverIP = "192.168.1.105";
	private final int PORT_NUM = 7777;

	private static HashMap<String,BaseFragment> fragments;
	private static HashMap<String,MyOnClickListener> listeners;
	private static FragmentManager manager;
	public static final String CUSTOMER = "customer";
	public static final String PIZZA = "pizza";
	public static final String ORDER_EDIT = "order_edit";
	public static final String MAIN_MENU = "main_menu";
	private ServerInterface serverInterface;
	private CallHandler callHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//create connection on app creation
		callHandler = new CallHandler();
		Client client = null;
		try {
			client = new Client(serverIP, PORT_NUM, callHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverInterface = (ServerInterface) client.getGlobal(ServerInterface.class);


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

		for(String key : listeners.keySet()){
			listeners.get(key).addInterface(serverInterface);
		}

		manager = getSupportFragmentManager();
		manager.beginTransaction().add(R.id.container, fragments.get(MAIN_MENU)).commit();
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
