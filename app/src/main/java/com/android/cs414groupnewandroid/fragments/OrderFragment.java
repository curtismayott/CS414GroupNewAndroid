package com.android.cs414groupnewandroid.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cs414groupnewandroid.R;
import com.android.cs414groupnewandroid.activities.MainActivity;
import com.android.cs414groupnewandroid.controllers.OrderEditListener;

public class OrderFragment extends BaseFragment {
	ListView orderList;
	public static ProgressDialog dialog;
	public static Dialog orderSentDialog;
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
		dialog = new ProgressDialog(getActivity());
		dialog.setMessage("Loading");
		orderSentDialog = new Dialog(getActivity());
		orderSentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		orderSentDialog.setContentView(R.layout.dialog_cancel);
		((TextView)orderSentDialog.findViewById(R.id.label)).setText("Order Sent");
		Button cancelDialog = (Button)orderSentDialog.findViewById(R.id.cancel_button);
		Button confirmDialog = (Button)orderSentDialog.findViewById(R.id.confirm_button);
		cancelDialog.setVisibility(View.GONE);
		confirmDialog.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		confirmDialog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.changeScreen(MainActivity.MAIN_MENU);
				orderSentDialog.dismiss();
			}
		});

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
		orderList.setOnItemLongClickListener((OrderEditListener) controller);

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

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		Log.e("OrderFragment", "--------ON ATTACH----------");
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.e("OrderFragment", "--------ON CREATE----------");
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser){
		super.setUserVisibleHint(isVisibleToUser);
		Log.e("OrderFragment", "--------SET USER VISIBLE HINT----------");
	}

	@Override
	public void onResume(){
		super.onResume();
		Log.e("OrderFragment", "--------ON RESUME----------");
		syncHandler.sendEmptyMessage(1);
		Log.e("OrderFragment", "blah");
		Toast.makeText(getActivity(), "OrderFrag :: setUserVisibleHint", Toast.LENGTH_SHORT).show();
	}

	public static Handler syncHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch (msg.what){
				case 1: // start
					createLoadingDialog();
					((OrderEditListener)MainActivity.listeners.get(MainActivity.ORDER_EDIT)).getServerToppings();
                    ((OrderEditListener)MainActivity.listeners.get(MainActivity.ORDER_EDIT)).getServerSauces();
                    ((OrderEditListener)MainActivity.listeners.get(MainActivity.ORDER_EDIT)).getServerSizes();
                    ((OrderEditListener)MainActivity.listeners.get(MainActivity.ORDER_EDIT)).getServerSides();
                    ((OrderEditListener)MainActivity.listeners.get(MainActivity.ORDER_EDIT)).getServerDrinks();
                    ((OrderEditListener)MainActivity.listeners.get(MainActivity.ORDER_EDIT)).getServerSpecials();
					break;
				case 2:	// finish
					MainActivity.listeners.get(MainActivity.ORDER_EDIT).resetView();
					dialog.dismiss();
					break;
				case 3:
					createLoadingDialog();
					break;
				case 4:
					createOrderCompleteDialog();
					dialog.dismiss();
					break;
			}
		}
	};

	public static void createLoadingDialog(){
		dialog.show();
	}
	public static void createOrderCompleteDialog(){
		orderSentDialog.show();
	}
}
