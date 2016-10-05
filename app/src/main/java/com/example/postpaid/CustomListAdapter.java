package com.example.postpaid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final ArrayList<String> itemname;
	private final ArrayList<String> imgid;
	 
	
	public CustomListAdapter(Context context, ArrayList<String> itemname,ArrayList<String> imgid) {
		super(context, R.layout.mylist, itemname);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.itemname=itemname;
		this.imgid=imgid;
		 
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView=inflater.inflate(R.layout.mylist, null,true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.Phone_Number);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView extratxt = (TextView) rowView.findViewById(R.id.Call_Date);
		
		txtTitle.setText(itemname.get(position));
		//imageView.setImageResource(imgid[position]);
		extratxt.setText(imgid.get(position));
		return rowView;
		
	};
}