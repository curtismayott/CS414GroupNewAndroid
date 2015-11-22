package com.android.cs414groupnewandroid.controllers;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.cs414groupnewandroid.R;
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
public class CustomerListener extends MyOnClickListener implements AdapterView.OnItemSelectedListener {
    Order order;
    public CustomerListener(Context context){
        super(context);
        components = new HashMap<>();
        orderID = -1;
    }

    @Override
    public void onClick(View v) {
        if((((Button)components.get("back"))).equals(v)) {
            clearEditTextFields();
            MainActivity.changeScreen(MainActivity.MAIN_MENU);
        } else if((((Button)components.get("save"))).equals(v)) {
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

			if(!verifyFields()) {
				System.out.println("OrderID: " + orderID);
				clearEditTextFields();
				MainActivity.passOrderID(MainActivity.ORDER_EDIT, orderID);
				MainActivity.changeScreen(MainActivity.ORDER_EDIT);
				orderID = -1;
				order = null;
			}else{
				showAlertFields();
			}
        }
    }
	public boolean verifyFields(){
		boolean showDialog = false;
		if(order.getOrderType() == ORDER_TYPE.CARRY_OUT &&
				(order.getCustomer().getName().equals("")
						|| order.getCustomer().getPhoneNumbers().get(0).getNumber().equals(""))
				){
			showDialog = true;
		}else if(order.getOrderType() == ORDER_TYPE.DELIVERY &&
				(order.getCustomer().getName().equals("")
						|| order.getCustomer().getAddress(0).getCity().equals("")
						|| order.getCustomer().getAddress(0).getState().equals("")
						|| order.getCustomer().getAddress(0).getZipcode().equals("")
						|| order.getCustomer().getAddress(0).getStreetAddress().equals("")
				)
				){
			showDialog = true;
		}else if(order.getOrderType() == ORDER_TYPE.PICK_UP && order.getCustomer().getName().equals("")){
			showDialog = true;
		}
		return showDialog;
	}
	public void showAlertFields(){
		final Dialog fieldRequire = new Dialog(context);
		fieldRequire.requestWindowFeature(Window.FEATURE_NO_TITLE);
		fieldRequire.setContentView(R.layout.dialog_cancel);
		((TextView)fieldRequire.findViewById(R.id.label)).setText("All of the fields must have information.");
		Button cancel = (Button)fieldRequire.findViewById(R.id.cancel_button);
		cancel.getLayoutParams().width = LinearLayout.LayoutParams.FILL_PARENT;
		Button confirm = (Button)fieldRequire.findViewById(R.id.confirm_button);
		confirm.setVisibility(View.GONE);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fieldRequire.dismiss();
			}
		});
		fieldRequire.show();
	}

    public void clearEditTextFields(){
		((EditText) components.get("phoneEditText")).setText("");
		((EditText) components.get("nameEditText")).setText("");
		((EditText) components.get("streetEditText")).setText("");
		((EditText) components.get("cityEditText")).setText("");
		((EditText) components.get("stateEditText")).setText("");
		((EditText) components.get("zipEditText")).setText("");
		((Spinner) components.get("orderTypeSpinner")).setSelection(((ArrayAdapter) ((Spinner) components.get("orderTypeSpinner")).getAdapter()).getPosition("Pickup"));
		(components.get("addressContainer")).setVisibility(View.GONE);
		(components.get("phoneContainer")).setVisibility(View.GONE);
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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(((TextView)view).getText().toString().equals("Carry Out")){
            (components.get("addressContainer")).setVisibility(View.GONE);
            (components.get("phoneContainer")).setVisibility(View.VISIBLE);
        }else if(((TextView)view).getText().toString().equals("Pickup")){
            (components.get("addressContainer")).setVisibility(View.GONE);
            (components.get("phoneContainer")).setVisibility(View.GONE);
        }else if(((TextView)view).getText().toString().equals("Delivery")){
            (components.get("addressContainer")).setVisibility(View.VISIBLE);
            (components.get("phoneContainer")).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}