package com.android.cs414groupnewandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.android.cs414groupnewandroid.R;

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
		TextView total = (TextView)rootView.findViewById(R.id.order_total);
		send.setOnClickListener(controller);
		total.setOnClickListener(controller);
		orderList.setOnItemClickListener(controller);

		controller.registerComponent("sendOrder", send);
		controller.registerComponent("totalField", total);
		controller.registerComponent("orderList", orderList);
		return rootView;
	}
}
