package com.android.cs414groupnewandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.cs414groupnewandroid.R;
import com.android.cs414groupnewandroid.objects.*;

import java.util.ArrayList;

/**
 * Created by darkbobo on 11/12/15.
 */
public class OrderListAdapter extends BaseAdapter {
	LayoutInflater inflater;
	Context context;
	ArrayList<OrderItem> orderItems;

	public OrderListAdapter(Context context, ArrayList<OrderItem> orderItems){
		this.context = context;
		this.orderItems = orderItems;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return orderItems.size();
	}

	@Override
	public Object getItem(int position) {
		return orderItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.row_order_list, parent, false);
			holder = new ViewHolder();
			holder.container = (LinearLayout)convertView.findViewById(R.id.container);
			holder.size = (TextView)convertView.findViewById(R.id.item_size);
			holder.sauce = (TextView)convertView.findViewById(R.id.item_sauce);
			holder.topping = (TextView)convertView.findViewById(R.id.item_topping);
			holder.price = (TextView)convertView.findViewById(R.id.item_price);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		OrderItem item = (OrderItem)getItem(position);
		if(item instanceof SideItem){
			holder.topping.setText(((SideItem) item).getName());
		}else{
			holder.size.setText(((Pizza)item).getSize().toString());
			holder.sauce.setText(((Pizza)item).getSauce().toString());
			StringBuilder toppings = new StringBuilder();
			toppings.setLength(0);
			for(int i = 0; i < ((Pizza)item).getToppingList().size(); i++){
				if(i != 0){
					toppings.append("\n");
				}
				toppings.append(((Pizza) item).getToppingList().get(i).getShortName());
			}
			if(toppings.length() != 0){
				holder.topping.setText(toppings.toString());
			}
		}

		holder.price.setText("$" + item.getPrice());
		return convertView;
	}

	public class ViewHolder{
		LinearLayout container;
		TextView size;
		TextView sauce;
		TextView topping;
		TextView price;
	}
}
