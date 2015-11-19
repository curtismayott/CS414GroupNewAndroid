package com.android.cs414groupnewandroid.controllers;

import android.util.Log;

import com.android.cs414groupnewandroid.objects.Order;
import com.android.cs414groupnewandroid.objects.Person;

import java.io.IOException;

import lipermi.handler.CallHandler;

/**
 * Created by darkbobo on 11/15/15.
 */
public class PizzaClient {
	private static PizzaClient server;
	public lipermi.net.Client client;
	private int PORT_NUMBER;
	private Person person;
	private Order order;
	private final String remoteHost = "192.168.1.105";

	public PizzaClient(){
		server = new PizzaClient();
		this.PORT_NUMBER = 7777;
	}

	public PizzaClient(int port){
		server = this;
		this.PORT_NUMBER = port;
	}

	public static PizzaClient getInstance(){
		return server;
	}

	public void setPerson(Person p){
		this.person = p;
	}

	public void setOrder(Order o){
		this.order = o;
	}


	public ServerInterface createConnection() {
		ServerInterface si = null;
		try {
			CallHandler callHandler = new CallHandler();
			client = new lipermi.net.Client(remoteHost, PORT_NUMBER, callHandler);
			si = (ServerInterface) client.getGlobal(ServerInterface.class);
			Log.d("OPEN_INTERFACE", "Client is connected to server");
		} catch (Exception e) {
			Log.d("OPEN_INTERFACE", "Client failed connecting to server");
			e.printStackTrace();
		}
		return si;
	}

	public void closeAndOpenClient() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		createConnection();
	}
}