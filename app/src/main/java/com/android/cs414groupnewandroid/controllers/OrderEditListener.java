package com.android.cs414groupnewandroid.controllers;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.cs414groupnewandroid.activities.MainActivity;
import com.android.cs414groupnewandroid.adapters.OrderListAdapter;
import com.android.cs414groupnewandroid.objects.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by darkbobo on 10/14/15.
 */
public class OrderEditListener extends MyOnClickListener implements AdapterView.OnItemClickListener {
	Order order;

	public OrderEditListener(Context context) {
		super(context);
		components = new HashMap<>();
	}

	public void setOrderID(int orderID) {
		order = model.getOrder(orderID);
		this.orderID = orderID;
	}

	@Override
	public void onClick(View v) {
		Pizza pizza = new Pizza();
		if (v.equals(components.get("savePizza"))) {
			ArrayList<Topping> selectedToppings = new ArrayList<>();
			int[] tmpSelectedToppings = ((ListView) components.get("pizzaToppingsList")).getc;

			for (int i = 0; i < ((ListView) components.get("pizzaToppingsList")).getAdapter().getCount(); i++) {
				if(((View)((ListView) components.get("pizzaToppingsList")).getAdapter().getItem(i)).isSelected()) {
					selectedToppings.add(model.getCatalog().getToppings().get(tmpSelectedToppings[i]));
				}
			}
			// add new pizza to order
			pizza.setToppingList(selectedToppings);
			pizza.setSauce(model.getCatalog().getSauces().get(((ListView) components.get("pizzaSaucesList")).getSelectedIndex()));
			pizza.setSize(model.getCatalog().getSizes().get(((ListView) components.get("pizzaSizesList")).getSelectedIndex()));
			pizza.calculatePrice();
			if (((Button) components.get("addPizzaButton")).getText().equals("Add")) {
				order.addPizza(pizza);
			} else {
				// update pizza
				int selectedIndex = ((ListView) components.get("pizzaList")).getSelectedIndex();
				order.updatePizza(selectedIndex, pizza);
			}
			resetView();
			clearPizzaSelections();
			((EditText) components.get("totalDisplay")).setText(model.TOTAL_TEXT + order.getOrderTotal());
		} else if (v.equals(components.get("cancelItem"))) {
			if (((Button) components.get("addPizzaButton")).getText().equals("Update")) {
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
			}
		} else if (v.equals(components.get("clearSelections"))) {
			clearPizzaSelections();
		} else if (v.equals(components.get("cancelOrder"))) {
			clearPizzaSelections();
			// clear pizzaList
			((ListView) components.get("pizzaList")).setListData(new String[0]);
			order.removeAllPizzas();
			if (model.getOrders().contains(order)) {
				model.getOrders().remove(order);
			}
			order = null;
			((EditText) components.get("totalDisplay")).setText(model.TOTAL_TEXT);
		} else if (v.equals(components.get("quitButton"))) {
			for (OrderItem o : order.getOrderItems()) {
				if (o.getStatus() == PIZZA_STATUS.NEW) {
					o.setStatus(PIZZA_STATUS.MAKELINE);
				}
			}
			Object[] exitOptions = {"Exit",
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
			MainActivity.activateWindow(MainActivity.ORDER_EDIT, MainActivity.ORDER_LIST);
		} else if (v.equals(components.get("sideButton")))

		{
			ArrayList<Side> sides = model.getCatalog().getSides();
			int sideSelection = JOptionPane.showOptionDialog(view,
					"Select a Side",
					"Sides",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					sides.toArray(),  //the titles of buttons
					sides.toArray()[0]); //default button title*/
			order.addSide(sides.get(sideSelection));
			resetView();
		} else if (v.equals("drinkButton"))

		{
			ArrayList<Drink> drinks = model.getCatalog().getDrinks();
			int drinkSelection = JOptionPane.showOptionDialog(view,
					"Select a Drink",
					"Drinks",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					drinks.toArray(),  //the titles of buttons
					drinks.toArray()[0]); //default button title*/
			order.addSide(drinks.get(drinkSelection));
			resetView();
		} else if (v.equals(components.get("sendOrder")))

		{
			order.sendPizzasToMakeLine();
			order.sendSidesToMakeLine();
			model.updateOrder(orderID, order);
			order = null;
			MainActivity.changeScreen(MainActivity.MAIN_MENU);
			// TODO : send order to server
		}

	}

	public void clearPizzaSelections() {
		((ListView) components.get("pizzaToppingsList")).clearSelection();
		((ListView) components.get("pizzaSizesList")).clearSelection();
		((ListView) components.get("pizzaSaucesList")).clearSelection();
		//((ListView)components.get("pizzaList")).clearSelection();
		((Button) components.get("addPizzaButton")).setText("Add");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		System.out.println(event.toString());
		ListView list = (ListView) event.getSource();

		if (list.equals(components.get("pizzaList"))) {

			((Button) components.get("addPizzaButton")).setText("Update");
			String buttonText = "";
			try {
				buttonText = list.getSelectedValue().toString();
			} catch (NullPointerException e) {
				((Button) components.get("addPizzaButton")).setText("Add");
			}
			Pizza pizza = null;
			for (Pizza p : order.getPizzas()) {
				if (p.toString().equals(buttonText)) {
					pizza = p;
				}
			}
			if (pizza != null) {
				((ListView) components.get("pizzaSaucesList")).setSelectedValue(pizza.getSauce(), true);
				((ListView) components.get("pizzaSizesList")).setSelectedValue(pizza.getSize(), true);

				ArrayList<Integer> indicies = new ArrayList<>();
				for (Topping t : pizza.getToppingList()) {
					for (int i = 0; i < model.getCatalog().getToppings().size(); i++) {
						if (model.getCatalog().getToppings().get(i).equals(t)) {
							indicies.add(i);
						}
					}
				}
				int[] tmpIndicies = new int[indicies.size()];
				for (int i = 0; i < indicies.size(); i++) {
					tmpIndicies[i] = indicies.get(i);
				}
				((ListView) components.get("pizzaToppingsList")).setSelectedIndices(tmpIndicies);
			} else {

				//resetView();
			}
		}
	}

	public void resetView() {
		OrderListAdapter adapter = new OrderListAdapter(context, order.getOrderItems());
		((ListView) components.get("orderList")).setAdapter(adapter);

		((ListView) components.get("pizzaSizesList")).setListData(model.getCatalog().getSizes().toArray());
		((ListView) components.get("pizzaSaucesList")).setListData(model.getCatalog().getSauces().toArray());
		((ListView) components.get("pizzaToppingsList")).setListData(model.getCatalog().getToppings().toArray());
		if (order != null) {
			if (order.getOrderItems().size() != 0) {
				((ListView) components.get("pizzaList")).setListData(order.getOrderItems().toArray());
				((EditText) components.get("totalDisplay")).setText(model.TOTAL_TEXT + order.getOrderTotal());
			} else {
				((ListView) components.get("pizzaList")).setListData(new String[0]);
				((EditText) components.get("totalDisplay")).setText("");
			}
		}
	}
}
