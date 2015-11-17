package com.android.cs414groupnewandroid.controllers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.cs414groupnewandroid.R;
import com.android.cs414groupnewandroid.activities.MainActivity;
import com.android.cs414groupnewandroid.adapters.OrderListAdapter;
import com.android.cs414groupnewandroid.adapters.SaucesAdapter;
import com.android.cs414groupnewandroid.adapters.SizeAdapter;
import com.android.cs414groupnewandroid.adapters.ToppingAdapter;
import com.android.cs414groupnewandroid.objects.*;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by darkbobo on 10/14/15.
 */
public class OrderEditListener extends MyOnClickListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
	Order order;
	Pizza activePizza;
	MyDialog drinkDialog;
	MyDialog sideDialog;
	MyDialog cancelItemDialog;
	MyDialog cancelOrderDialog;

	public OrderEditListener(Context context) {
		super(context);
		components = new HashMap<>();
	}

	public void setOrderID(int orderID) {
		order = model.getOrder(orderID);
		this.orderID = orderID;
	}

	public void setPizza(int pizzaID){
		activePizza = order.getPizzaByID(pizzaID);
	}

	public void clearPizza(){
		activePizza = null;
	}

	public void setPizzaScreen(){
		((ToppingAdapter)((ListView) components.get("toppingsList")).getAdapter()).setSelectedToppings(getSelectedToppings(activePizza));
		((SizeAdapter)((ListView) components.get("sizesList")).getAdapter()).setSelectedSize(activePizza.getSize());
		((SaucesAdapter)((ListView) components.get("saucesList")).getAdapter()).setSelectedSauce(activePizza.getSauce());
	}

	public ArrayList<Integer> getSelectedToppings(Pizza p){
		ArrayList<Integer> toppings = new ArrayList<>();
		for(int i = 0; i < model.getCatalog().getToppings().size(); i++){
			for(Topping pt : p.getToppingList()){
				if(model.getCatalog().getToppings().get(i).equals(pt)){
					toppings.add(i);
				}
			}
		}
		return toppings;
	}

	@Override
	public void onClick(View v) {
		Pizza pizza = new Pizza();
		if (v.equals(components.get("savePizza"))) {
			ArrayList<Boolean> selectedToppings = ((ToppingAdapter)((ListView) components.get("toppingsList")).getAdapter()).getSelected();
			ArrayList<Topping> toppings = new ArrayList<>();
			for (int i = 0; i < selectedToppings.size(); i++) {
				if(selectedToppings.get(i)){
					toppings.add(model.getCatalog().getToppings().get(i));
				}
			}
			// add new pizza to order
			pizza.setToppingList(toppings);
			pizza.setSauce(model.getCatalog().getSauces().get(((SaucesAdapter) ((ListView) components.get("saucesList")).getAdapter()).getSelected()));
			pizza.setSize(model.getCatalog().getSizes().get(((SizeAdapter)((ListView)components.get("sizesList")).getAdapter()).getSelected()));
			pizza.calculatePrice();
			// add/update pizza
			// TODO
			if(activePizza == null){
				order.savePizza(-1, pizza);
			}else {
				order.savePizza(activePizza.getItemID(), pizza);
			}
			resetView();
			clearPizza();
			clearPizzaSelections();
			((TextView) components.get("totalDisplay")).setText("$" + Double.toString(order.getOrderTotal()));
			MainActivity.changeScreen(MainActivity.ORDER_EDIT);
		} else if(v.equals(components.get("cancelPizza"))){
			MainActivity.changeScreen(MainActivity.ORDER_EDIT);
		} else if (v.equals(components.get("cancelItem"))) {
			/*if (((Button) components.get("addPizzaButton")).getText().equals("Update")) {
				int delete = JOptionPane.showConfirmDialog(view,
						"Remove Item?",
						"Remove",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (delete == 0) {
					((Button) components.get("addPizzaButton")).setText("Add");

					Pizza removedPizza = null;
					for (Pizza p : order.getPizzas()) {
						if (p.toString().equals(((ListView) components.get("pizzaList")).getSelectedValue().toString())) {
							removedPizza = p;
						}
					}
					if (removedPizza != null) {
						order.removePizza(removedPizza);

						resetView();
						System.out.println(order.getPizzas().size());
					}
				}
				((ListView) components.get("pizzaList")).setListData(order.getOrderItems().toArray());
				clearPizzaSelections();
			}*/
		} else if (v.equals(components.get("clearSelections"))) {
			clearPizzaSelections();
		} else if (v.equals(components.get("cancelOrder"))) {
			cancelOrderDialog = new MyDialog(context);
			cancelOrderDialog.init();
			cancelOrderDialog.setContentView(R.layout.dialog_cancel);
			TextView label = (TextView)cancelOrderDialog.findViewById(R.id.label);
			label.setText("Cancel Order");
			Button cancel = (Button)cancelOrderDialog.findViewById(R.id.cancel_button);
			cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					cancelOrderDialog.dismiss();
				}
			});
			Button confirm = (Button)cancelOrderDialog.findViewById(R.id.confirm_button);
			confirm.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					order = null;
					activePizza = null;
					clearPizzaSelections();
					((TextView) components.get("totalDisplay")).setText("");
					cancelOrderDialog.dismiss();
					MainActivity.changeScreen(MainActivity.MAIN_MENU);
				}
			});
			cancelOrderDialog.show();

			/*Object[] exitOptions = {"Exit",
					"Cancel"};
			int n = JOptionPane.showOptionDialog(view,
					"If updating order, changes will be saved.\n"
							+ "If new order, the order will be removed.",
					"Confirm",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					exitOptions,  //the titles of buttons
					exitOptions[0]); //default button title
			if (n == JOptionPane.YES_OPTION) {
				model.removeOrder(order.getOrderID());
				order = null;
				resetView();
				MainActivity.activateWindow(MainActivity.ORDER_EDIT, MainActivity.ORDER_LIST);
			} else {
				// do nothing
			}
			MainActivity.activateWindow(MainActivity.ORDER_EDIT, MainActivity.ORDER_LIST);*/
		} else if(v.equals(components.get("addPizzaButton")))
		{
			clearPizza();
			MainActivity.changeScreen(MainActivity.PIZZA);
		} else if (v.equals(components.get("sideButton"))) {
			sideDialog = new MyDialog(context);
			sideDialog.init();
			sideDialog.setContentView(R.layout.dialog_item);
			LinearLayout container = (LinearLayout)sideDialog.findViewById(R.id.items_list);
			for(final Side s : model.getCatalog().getSides()){
				Button button = new Button(context);
				button.setText(s.toString());
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						order.addSide(s);
						sideDialog.dismiss();
						resetView();
					}
				});
				container.addView(button);
			}
			sideDialog.show();
		} else if (v.equals(components.get("drinkButton")))
		{
			drinkDialog = new MyDialog(context);
			drinkDialog.init();
			drinkDialog.setContentView(R.layout.dialog_item);
			LinearLayout container = (LinearLayout)drinkDialog.findViewById(R.id.items_list);
			for(final Drink d : model.getCatalog().getDrinks()){
				Button button = new Button(context);
				button.setText(d.toString());
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						order.addSide(d);
						drinkDialog.dismiss();
						resetView();
					}
				});
				container.addView(button);
			}
			drinkDialog.show();
		} else if (v.equals(components.get("sendOrder")))
		{
			order.sendPizzasToMakeLine();
			order.sendSidesToMakeLine();
			model.updateOrder(orderID, order);
			MainActivity.changeScreen(MainActivity.MAIN_MENU);
			Log.e("OrderEditListener", order.toString());
			PizzaServer.getInstance().setOrder(order);
			order.sendOrder(order);
			order = null;
			// TODO : send order to server
		}
	}

	public void clearPizzaSelections() {
		if(activePizza != null) {
			((ToppingAdapter) ((ListView) components.get("toppingsList")).getAdapter()).unselectAll();
			((SizeAdapter) ((ListView) components.get("sizesList")).getAdapter()).unselectAll();
			((SaucesAdapter) ((ListView) components.get("saucesList")).getAdapter()).unselectAll();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(parent.equals(components.get("orderList"))){
			if(parent.getAdapter().getItem(position) instanceof Pizza){
				setPizza(((Pizza) parent.getAdapter().getItem(position)).getItemID());
				Log.e("OrderEditListener", "PizzaID: " + activePizza.getItemID());
				MainActivity.changeScreen(MainActivity.PIZZA);
			}else{
				// is drink or side
			}
		} else if (parent.equals(components.get("sizesList"))) {
			for(int i = 0; i < parent.getAdapter().getCount(); i++){
				((SizeAdapter)parent.getAdapter()).setSelected(i, false);
			}
			((SizeAdapter)parent.getAdapter()).setSelected(position, true);
		} else if (parent.equals(components.get("saucesList"))) {
			for(int i = 0; i < parent.getAdapter().getCount(); i++){
				((SaucesAdapter)parent.getAdapter()).setSelected(i, false);
			}
			((SaucesAdapter)parent.getAdapter()).setSelected(position, true);
		} else if (parent.equals(components.get("toppingsList"))) {
			if(((ToppingAdapter)parent.getAdapter()).isSelected(position)) {
				((ToppingAdapter) parent.getAdapter()).setSelected(position, false);
			}else{
				((ToppingAdapter) parent.getAdapter()).setSelected(position, true);
			}
		}
	}

	public void resetView() {
		Log.e("OrderEditListener", "" + orderID);
		if(components.get("orderList") != null) {
			if (order.getOrderItems().size() > 0) {
				OrderListAdapter adapter = new OrderListAdapter(context, order.getOrderItems());
				((ListView) components.get("orderList")).setAdapter(adapter);
			} else {
				((ListView) components.get("orderList")).setAdapter(null);
			}
		}
		if(components.get("saucesList") != null) {
			SaucesAdapter saucesAdapter = new SaucesAdapter(context, model.getCatalog().getSauces());
			((ListView) components.get("saucesList")).setAdapter(saucesAdapter);
		}
		if(components.get("sizesList") != null) {
			SizeAdapter sizeAdapter = new SizeAdapter(context, model.getCatalog().getSizes());
			((ListView) components.get("sizesList")).setAdapter(sizeAdapter);
		}
		if(components.get("toppingsList") != null) {
			ToppingAdapter toppingAdapter = new ToppingAdapter(context, model.getCatalog().getToppings());
			((ListView) components.get("toppingsList")).setAdapter(toppingAdapter);
		}
		if(activePizza != null){
			setPizzaScreen();
		}
		if(components.get("totalDisplay") != null) {
			if (order != null) {
				if (order.getOrderItems().size() != 0) {
					//((ListView) components.get("pizzaList")).setListData(order.getOrderItems().toArray());
					((TextView) components.get("totalDisplay")).setText("$" + Double.toString(order.getOrderTotal()));
				} else {
					//((ListView) components.get("pizzaList")).setListData(new String[0]);
					((TextView) components.get("totalDisplay")).setText("");
				}
			}
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		cancelItemDialog = new MyDialog(context);
		cancelItemDialog.init();
		cancelItemDialog.setContentView(R.layout.dialog_cancel);
		TextView label = (TextView)cancelItemDialog.findViewById(R.id.label);
		Button cancel = (Button)cancelItemDialog.findViewById(R.id.cancel_button);
		Button confirm = (Button)cancelItemDialog.findViewById(R.id.confirm_button);
		label.setText("Cancel Item:\n" + parent.getAdapter().getItem(position).toString());
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelItemDialog.dismiss();
			}
		});
		final AdapterView<?> tParent = parent;
		final int tPosition = position;
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tParent.getAdapter().getItem(tPosition) instanceof Pizza){
					order.removePizza((Pizza)tParent.getAdapter().getItem(tPosition));
				}else {
					order.removeItem((OrderItem) tParent.getAdapter().getItem(tPosition));
				}
				resetView();
				cancelItemDialog.dismiss();
			}
		});
		cancelItemDialog.show();
		return true;
	}
}