package com.android.cs414groupnewandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.cs414groupnewandroid.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {
	public MainActivityFragment() {
	}

	public static MainActivityFragment newInstance(){
		MainActivityFragment fragment = new MainActivityFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		Button newOrder = (Button)rootView.findViewById(R.id.new_order);
		controller.registerComponent("newOrder", newOrder);
		newOrder.setOnClickListener(controller);
		return rootView;
	}
}
