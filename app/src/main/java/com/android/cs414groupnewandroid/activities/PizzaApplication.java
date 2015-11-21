package com.android.cs414groupnewandroid.activities;

import android.app.Application;
import android.content.Context;

/**
 * Created by darkbobo on 11/21/15.
 */
public class PizzaApplication extends Application {
	public static Context context;


	private static PizzaApplication instance;

	public static PizzaApplication getInstance()
	{
		return instance;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		context = this.getApplicationContext();
	}
}
