package com.android.cs414groupnewandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.cs414groupnewandroid.R;

import java.util.List;

public class PizzaFragment extends BaseFragment {
	ListView sizesList;
	ListView saucesList;
	ListView toppingsList;

	public static PizzaFragment newInstance() {
		PizzaFragment fragment = new PizzaFragment();
		return fragment;
	}

	public PizzaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pizza, container, false);
		sizesList = (ListView)rootView.findViewById(R.id.pizza_size_list);
		saucesList = (ListView)rootView.findViewById(R.id.pizza_sauce_list);
		toppingsList = (ListView)rootView.findViewById(R.id.pizza_toppings_list);
		Button cancel = (Button)rootView.findViewById(R.id.pizza_cancel);
		Button confirm = (Button)rootView.findViewById(R.id.pizza_confirm);

		controller.registerComponent("sizesList", sizesList);
		controller.registerComponent("saucesList", saucesList);
		controller.registerComponent("toppingsList", toppingsList);
		controller.registerComponent("cancelPizza", cancel);
		controller.registerComponent("savePizza", confirm);

		sizesList.setOnItemClickListener(controller);
		saucesList.setOnItemClickListener(controller);
		toppingsList.setOnItemClickListener(controller);
		cancel.setOnClickListener(controller);
		confirm.setOnClickListener(controller);
		controller.resetView();
		return rootView;
	}
}
