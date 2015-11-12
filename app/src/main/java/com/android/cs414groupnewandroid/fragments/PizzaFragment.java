package com.android.cs414groupnewandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.cs414groupnewandroid.R;

public class PizzaFragment extends BaseFragment {
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

		return rootView;
	}
}
