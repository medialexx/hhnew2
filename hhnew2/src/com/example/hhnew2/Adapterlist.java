package com.example.hhnew2;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapterlist extends ArrayAdapter<Pars> {
ArrayList<Pars> messages;
LayoutInflater inflater;
	public Adapterlist(Context context, int textViewResourceId,
			ArrayList<Pars> objects) {
		super(context, textViewResourceId, objects);
		messages=objects;
		inflater=LayoutInflater.from(context);
		// TODO Auto-generated constructor stub
	}

	static class ViewList
	{
		public ImageView imageV;
		public TextView titleView;
		public TextView despView;
		public TextView dateView;
		public TextView catView;
		
		
	}
	@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			 ViewList holder;
			 if (convertView == null) {
			        convertView = inflater.inflate(R.layout.temp_listview, null, true);
			        holder = new ViewList();
			        holder.imageV=(ImageView) convertView.findViewById(R.id.imageView);
			        holder.titleView = (TextView) convertView.findViewById(R.id.textTitle);
			        holder.despView=(TextView) convertView.findViewById(R.id.textDesk);
			        holder.dateView = (TextView) convertView.findViewById(R.id.textDate);
			        holder.catView=(TextView) convertView.findViewById(R.id.textCategory);
			        convertView.setTag(holder);
			    } else {
			        holder = (ViewList) convertView.getTag();
			    }
			 
			    holder.titleView.setText(messages.get(position).title);
			    holder.despView.setText(messages.get(position).description);
			    holder.dateView.setText(messages.get(position).date);
			    String temp=getContext().getResources().getString(R.string.cat);
			    holder.catView.setText(temp + messages.get(position).category);
		        if (holder.imageV != null) {
		            new ImageDownloaderTask(holder.imageV).execute(messages.get(position).image);
		        }
		 
			    return convertView;
			}

}
