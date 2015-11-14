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
import com.android.cs414groupnewandroid.objects.PizzaSize;

import java.util.ArrayList;

/**
 * Created by darkbobo on 11/12/15.
 */
public class SizeAdapter extends BaseAdapter {
	LayoutInflater inflater;
	Context context;
	ArrayList<PizzaSize> sizes;
	ArrayList<Boolean> selected;

	public SizeAdapter(Context context, ArrayList<PizzaSize> sizes){
		this.context = context;
		this.sizes = sizes;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		selected = new ArrayList<>();
		for(int i = 0; i < sizes.size(); i++){
			selected.add(false);
		}
	}

	@Override
	public int getCount() {
		return sizes.size();
	}

	@Override
	public Object getItem(int position) {
		return sizes.get(position);
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

	public int getSelected(){
		for(int i = 0; i < selected.size(); i++){
			if(selected.get(i)){
				return i;
			}
		}
		return -1;
	}

	public void unselectAll(){
		for(int i = 0; i < selected.size(); i++){
			selected.set(i, false);
		}
		notifyDataSetChanged();
	}

	public void setSelectedSize(PizzaSize size){
		unselectAll();
		for(int i = 0; i < sizes.size(); i++){
			if(sizes.get(i).equals(size)){
				setSelected(i, true);
				break;
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.row_size, parent, false);
			holder = new ViewHolder();
			holder.container = (LinearLayout)convertView.findViewById(R.id.container);
			holder.size = (TextView)convertView.findViewById(R.id.item_size);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		PizzaSize item = (PizzaSize)getItem(position);
		holder.size.setText(item.getFullName());
		if(isSelected(position)){
			holder.container.setBackgroundColor(Color.BLUE);
		}else{
			holder.container.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}

	public class ViewHolder{
		LinearLayout container;
		TextView size;
	}
}
