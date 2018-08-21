package com.example.a76952.login2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 76952 on 2018/8/11.
 */

public class ActivityItemAdapter extends ArrayAdapter<ActivityItem> {
    private int resourceId;
    public ActivityItemAdapter(Context context,int textViewResourceId,List<ActivityItem> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ActivityItem item=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView itemName=(TextView)view.findViewById(R.id.item_name);
        TextView itemTime=(TextView)view.findViewById(R.id.item_time);
        itemName.setText(item.getActivityName());
        itemTime.setText(item.getActivityTime());
        return view;
    }
}
