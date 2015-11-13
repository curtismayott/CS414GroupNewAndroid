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
import com.android.cs414groupnewandroid.objects.Sauce;

import java.util.ArrayList;

/**
 * Created by darkbobo on 11/12/15.
 */
public class SaucesAdapter extends BaseAdapter {
	LayoutInflater inflater;
	Context context;
	ArrayList<Sauce> sauces;
	ArrayList<Boolean> selected;

	public SaucesAdapter(Context context, ArrayList<Sauce> sauces){
		this.context = context;
		this.sauces = sauces;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		selected = new ArrayList<>();
		for(int i = 0; i < sauces.size(); i++){
			selected.add(false);
		}
	}

	@Override
	public int getCount() {
		return sauces.size();
	}

	@Override
	public Object getItem(int position) {
		return sauces.get(position);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.row_sauce, parent, false);
			holder = new ViewHolder();
			holder.container = (LinearLayout)convertView.findViewById(R.id.container);
			holder.sauce = (TextView)convertView.findViewById(R.id.item_sauce);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		Sauce item = (Sauce)getItem(position);
		holder.sauce.setText(item.getFullName());
		if(isSelected(position)){
			holder.container.setBackgroundColor(Color.BLUE);
		}else{
			holder.container.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}

	public class ViewHolder{
		LinearLayout container;
		TextView sauce;
	}
}