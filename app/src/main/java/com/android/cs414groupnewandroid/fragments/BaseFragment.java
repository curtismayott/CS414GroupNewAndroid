package com.android.cs414groupnewandroid.fragments;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.cs414groupnewandroid.R;
import com.android.cs414groupnewandroid.controllers.MyOnClickListener;
import com.android.cs414groupnewandroid.objects.Order;

public class BaseFragment extends Fragment {
	public Order order;
	MyOnClickListener controller;
	public static final int UPDATE = 1;
	public static final int PASS_ID = 2;

	public BaseFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message m) {
			switch (m.what) {
				case 1:
					setOrderID(m.arg1);
					break;
				case 2:
					updateView();
					break;
			}
		}
	};

	public static void setOrderID(int orderID){

	}

	public static void updateView(){

	}

	public void setController(MyOnClickListener controller){
		this.controller = controller;
	}
}
