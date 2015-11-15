package com.android.cs414groupnewandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.android.cs414groupnewandroid.R;
import com.android.cs414groupnewandroid.controllers.OrderEditListener;

public class OrderFragment extends BaseFragment {
	ListView orderList;
	public static OrderFragment newInstance() {
		OrderFragment fragment = new OrderFragment();
		return fragment;
	}

	public OrderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_order, container, false);
		orderList = (ListView)rootView.findViewById(R.id.order_list);
		Button send = (Button)rootView.findViewById(R.id.order_send);
		Button cancel = (Button)rootView.findViewById(R.id.order_cancel);
		TextView total = (TextView)rootView.findViewById(R.id.order_total);
		Button addPizza = (Button)rootView.findViewById(R.id.order_pizza);
		Button addDrink = (Button)rootView.findViewById(R.id.order_drink);
		Button addSide = (Button)rootView.findViewById(R.id.order_side);

		send.setOnClickListener(controller);
		cancel.setOnClickListener(controller);
		total.setOnClickListener(controller);
		addPizza.setOnClickListener(controller);
		addDrink.setOnClickListener(controller);
		addSide.setOnClickListener(controller);
		orderList.setOnItemClickListener(controller);
		orderList.setOnItemLongClickListener((OrderEditListener)controller);

		controller.registerComponent("sendOrder", send);
		controller.registerComponent("cancelOrder", cancel);
		controller.registerComponent("totalDisplay", total);
		controller.registerComponent("orderList", orderList);
		controller.registerComponent("addPizzaButton", addPizza);
		controller.registerComponent("sideButton", addSide);
		controller.registerComponent("drinkButton", addDrink);
		controller.resetView();
		return rootView;
	}
}
