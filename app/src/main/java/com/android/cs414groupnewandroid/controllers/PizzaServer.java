package com.android.cs414groupnewandroid.controllers;

import com.android.cs414groupnewandroid.objects.Order;
import com.android.cs414groupnewandroid.objects.Person;

import lipermi.handler.CallHandler;
import lipermi.net.Server;

/**
 * Created by darkbobo on 11/15/15.
 */
public class PizzaServer extends Server {
	private static PizzaServer server;
	private Person person;
	private Order order;

	private PizzaServer(){
		CallHandler handler = new CallHandler();
	}

	public static PizzaServer getInstance(){
		if(server != null){
			server = new PizzaServer();
		}
		return server;
	}

	public void setPerson(Person p){
		this.person = p;
	}

	public void setOrder(Order o){
		this.order = o;
	}
}
