package com.android.cs414groupnewandroid.controllers;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.cs414groupnewandroid.activities.MainActivity;
import com.android.cs414groupnewandroid.objects.Address;
import com.android.cs414groupnewandroid.objects.ORDER_TYPE;
import com.android.cs414groupnewandroid.objects.Order;
import com.android.cs414groupnewandroid.objects.Person;
import com.android.cs414groupnewandroid.objects.Phone;

import java.util.HashMap;

/**
 * Created by darkbobo on 10/24/15.
 */
public class CustomerListener extends MyOnClickListener {
    Order order;
    public CustomerListener(){
        components = new HashMap<>();
        orderID = -1;
    }

    @Override
    public void onClick(View v) {
        if((components.get("cancelButton")).equals(v)) {
            clearEditTextFields();
            MainActivity.changeScreen(MainActivity.MAIN_MENU);
        } else if((components.get("clearButton")).equals(v)) {
            clearEditTextFields();
        } else if((components.get("saveButton")).equals(v)) {
            Address address = new Address();
            address.setStreetAddress(((EditText) components.get("streetEditText")).getText().toString());
            address.setCity(((EditText) components.get("cityEditText")).getText().toString());
            address.setState(((EditText) components.get("stateEditText")).getText().toString());
            address.setZipcode(((EditText) components.get("zipEditText")).getText().toString());

            Phone phone = new Phone();
            phone.setNumber(((EditText) components.get("phoneEditText")).getText().toString());

            Person person = new Person(((EditText) components.get("nameEditText")).getText().toString(), address, phone);

            if (order == null) {
                order = new Order();
                order.setCustomer(person);
                orderID = model.addOrder(order);
            } else {
                System.out.println("CustomerListener OrderID about to send: " + order.getOrderID());
                order.setCustomer(person);
            }
            if(((Spinner)components.get("orderTypeSpinner")).getSelectedItem().toString().equals("Pickup")){
                order.setOrderType(ORDER_TYPE.PICK_UP);
            }else if(((Spinner)components.get("orderTypeSpinner")).getSelectedItem().toString().equals("Carry Out")){
                order.setOrderType(ORDER_TYPE.CARRY_OUT);
            }else if(((Spinner)components.get("orderTypeSpinner")).getSelectedItem().toString().equals("Delivery")){
                order.setOrderType(ORDER_TYPE.DELIVERY);
            }

            System.out.println("OrderID: " + orderID);
            MainActivity.passOrderID(MainActivity.ORDER_EDIT, orderID);
            MainActivity.changeScreen(MainActivity.ORDER_EDIT);
            orderID = -1;
            order = null;
        }
    }

    public void clearEditTextFields(){
        ((EditText) components.get("phoneEditText")).setText("");
        ((EditText) components.get("nameEditText")).setText("");
        ((EditText) components.get("streetEditText")).setText("");
        ((EditText) components.get("cityEditText")).setText("");
        ((EditText) components.get("stateEditText")).setText("");
        ((EditText) components.get("zipEditText")).setText("");
    }

    @Override
    public void setOrderID(int orderID){
        if(orderID >= 0) {
            order = model.getOrder(orderID);
            this.orderID = orderID;
            System.out.println("CustomerListener OrderID: " + order.getOrderID());
            ((EditText) components.get("phoneEditText")).setText(order.getCustomer().getPhoneNumbers().get(0).getNumber());
            ((EditText) components.get("nameEditText")).setText(order.getCustomer().getName());
            ((EditText) components.get("streetEditText")).setText(order.getCustomer().getAddress(0).getStreetAddress());
            ((EditText) components.get("cityEditText")).setText(order.getCustomer().getAddress(0).getCity());
            ((EditText) components.get("stateEditText")).setText(order.getCustomer().getAddress(0).getState());
            ((EditText) components.get("zipEditText")).setText(order.getCustomer().getAddress(0).getZipcode());
            if(order.getOrderType() == ORDER_TYPE.PICK_UP){
                ((Spinner)components.get("orderTypeSpinner")).setSelection(((ArrayAdapter)((Spinner) components.get("orderTypeSpinner")).getAdapter()).getPosition("Pickup"));
				(components.get("addressContainer")).setVisibility(View.GONE);
				(components.get("phoneContainer")).setVisibility(View.VISIBLE);
            }else if(order.getOrderType() == ORDER_TYPE.CARRY_OUT){
				((Spinner)components.get("orderTypeSpinner")).setSelection(((ArrayAdapter) ((Spinner) components.get("orderTypeSpinner")).getAdapter()).getPosition("Carry Out"));
				(components.get("addressContainer")).setVisibility(View.GONE);
				(components.get("phoneContainer")).setVisibility(View.VISIBLE);
            }else if(order.getOrderType() == ORDER_TYPE.DELIVERY){
				((Spinner)components.get("orderTypeSpinner")).setSelection(((ArrayAdapter) ((Spinner) components.get("orderTypeSpinner")).getAdapter()).getPosition("Delivery"));
				(components.get("addressContainer")).setVisibility(View.VISIBLE);
				(components.get("phoneContainer")).setVisibility(View.VISIBLE);
            }
        }else{
            clearEditTextFields();
        }
        components.get("phoneEditText").requestFocus();
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(((Spinner)view).getSelectedItem().equals("Carry Out")){
			(components.get("addressContainer")).setVisibility(View.GONE);
			(components.get("phoneContainer")).setVisibility(View.VISIBLE);
		}else if(((Spinner)view).getSelectedItem().equals("Pickup")){
			(components.get("addressContainer")).setVisibility(View.GONE);
			(components.get("phoneContainer")).setVisibility(View.VISIBLE);
		}else if(((Spinner)view).getSelectedItem().equals("Delivery")){
			(components.get("addressContainer")).setVisibility(View.VISIBLE);
			(components.get("phoneContainer")).setVisibility(View.VISIBLE);
		}
	}
}
