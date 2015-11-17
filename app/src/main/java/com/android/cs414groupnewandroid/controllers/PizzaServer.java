package com.android.cs414groupnewandroid.controllers;

import android.util.Log;

import com.android.cs414groupnewandroid.objects.Order;
import com.android.cs414groupnewandroid.objects.OrderInterface;
import com.android.cs414groupnewandroid.objects.Person;

import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.Client;
import lipermi.net.Server;

/**
 * Created by darkbobo on 11/15/15.
 */
public class PizzaServer extends Server {
	private static PizzaServer server;
	private Person person;
	private OrderInterface order;
	CallHandler handler;

	private PizzaServer(){
		handler = new CallHandler();
	}

	public static PizzaServer getInstance(){
		if(server == null){
			server = new PizzaServer();
		}
		return server;
	}

	public void setPerson(Person p){
		this.person = p;
	}

	public void setOrder(Order o){
		Log.e("PizzaServer", "setOrder: " + o.toString());
		this.order = o;
		try {
			handler.registerGlobal(OrderInterface.class, order);
			String url = "localhost";
			int port = 9587;
			server.bind(port, handler);
			Client client = new Client(url, port, handler);
			OrderInterface remoteObject;
			remoteObject = (OrderInterface) client.getGlobal(OrderInterface.class);
			remoteObject.sendOrder(o);
		} catch (Exception e) {
			Log.e("PizzaServer", "error");
		}
	}
}
