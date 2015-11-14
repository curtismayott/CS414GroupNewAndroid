package com.android.cs414groupnewandroid.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.cs414groupnewandroid.R;
import com.android.cs414groupnewandroid.objects.Topping;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by darkbobo on 11/12/15.
 */
public class ToppingAdapter extends BaseAdapter {
	LayoutInflater inflater;
	Context context;
	ArrayList<Topping> toppings;
	ArrayList<Boolean> selected;

	public ToppingAdapter(Context context, ArrayList<Topping> toppings){
		this.context = context;
		this.toppings = toppings;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		selected = new ArrayList<>();
		for(int i = 0; i < toppings.size(); i++){
			selected.add(false);
		}
	}

	@Override
	public int getCount() {
		return toppings.size();
	}

	@Override
	public Object getItem(int position) {
		return toppings.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setSelected(int index, boolean isSelected){
		selected.set(index, isSelected);
		notifyDataSetChanged();
	}

	public boolean isSelected(int position){
		return selected.get(position);
	}

	public ArrayList<Boolean> getSelected(){
		return selected;
	}

	public void unselectAll(){
		for(int i = 0; i < selected.size(); i++){
			selected.set(i, false);
		}
		notifyDataSetChanged();
	}

	public void setSelectedToppings(ArrayList<Integer> indicies){
		unselectAll();
		for(Integer i : indicies){
			setSelected(i, true);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.row_order_list, parent, false);
			holder = new ViewHolder();
			holder.container = (LinearLayout)convertView.findViewById(R.id.container);
			holder.topping = (TextView)convertView.findViewById(R.id.item_topping);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		Topping item = (Topping)getItem(position);
		holder.topping.setText(item.getFullName());
		if(isSelected(position)){
			holder.container.setBackgroundColor(Color.BLUE);
		}else{
			holder.container.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}

	public class ViewHolder{
		LinearLayout container;
		TextView topping;
	}
}