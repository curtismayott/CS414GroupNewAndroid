package com.android.cs414groupnewandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.android.cs414groupnewandroid.R;
import com.android.cs414groupnewandroid.controllers.CustomerListener;
import com.android.cs414groupnewandroid.controllers.MyOnClickListener;

public class CustomerFragment extends BaseFragment {
	public static CustomerFragment newInstance() {
		CustomerFragment fragment = new CustomerFragment();
		return fragment;
	}

	public CustomerFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_customer, container, false);
		Button save = (Button)rootView.findViewById(R.id.customer_save);
		Button back = (Button)rootView.findViewById(R.id.customer_back);
		Spinner orderTypeSpinner = (Spinner)rootView.findViewById(R.id.order_type_spinner);

		save.setOnClickListener(controller);
		back.setOnClickListener(controller);
		orderTypeSpinner.setOnItemSelectedListener((CustomerListener)controller);

		controller.registerComponent("save", save);
		controller.registerComponent("back", back);
		controller.registerComponent("orderTypeSpinner", orderTypeSpinner);

		controller.registerComponent("phoneContainer", rootView.findViewById(R.id.phone_container));
		controller.registerComponent("phoneEditText", rootView.findViewById(R.id.phone_field));
		controller.registerComponent("nameEditText", rootView.findViewById(R.id.name_field));
		controller.registerComponent("streetEditText", rootView.findViewById(R.id.street_field));
		controller.registerComponent("cityEditText", rootView.findViewById(R.id.city_field));
		controller.registerComponent("stateEditText", rootView.findViewById(R.id.state_field));
		controller.registerComponent("zipEditText", rootView.findViewById(R.id.zip_field));
		controller.registerComponent("addressContainer", rootView.findViewById(R.id.address_container));
		return rootView;
	}

	public static void updateView(){

	}

	public static void setOrderID(int orderID){

	}
}
