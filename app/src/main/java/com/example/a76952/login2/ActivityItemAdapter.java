package com.example.a76952.login2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 76952 on 2018/8/11.
 */

//public class ActivityItemAdapter extends ArrayAdapter<ActivityItem> {
//    private int resourceId;
//    public ActivityItemAdapter(Context context,int textViewResourceId,List<ActivityItem> objects){
//        super(context,textViewResourceId,objects);
//        resourceId=textViewResourceId;
//    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent){
//        ActivityItem item=getItem(position);
//        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
//        TextView itemName=(TextView)view.findViewById(R.id.item_name);
//        TextView itemTime=(TextView)view.findViewById(R.id.item_time);
//        itemName.setText(item.getActivityName());
//        itemTime.setText(item.getActivityTime());
//        return view;
//    }
//}
public class ActivityItemAdapter extends RecyclerView.Adapter<ActivityItemAdapter.ViewHolder> {

    private List<ActivityItem> activityList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View activityView;
        ImageView activityImage;
        TextView activityName;
        TextView activityID;

        public ViewHolder (View view) {
            super(view);
            activityView = view;
            activityImage = (ImageView)view.findViewById(R.id.item_activity_image);
            activityName = (TextView)view.findViewById(R.id.item_activity_name);
            activityID = (TextView)view.findViewById(R.id.item_activity_id);
        }
    }

    public ActivityItemAdapter(List<ActivityItem> list) {
        activityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.activityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                ActivityItem activity = activityList.get(position);

                Intent to_activity_info = new Intent(view.getContext(), ActivityDetail.class);
//                to_activity_info.putExtra("name", activity.getName());
//                to_activity_info.putExtra("info", activity.getInfo());
//                to_activity_info.putExtra("image", activity.getImage());
                to_activity_info.putExtra("id", activity.getId());
                to_activity_info.putExtra("creater", activity.getCreater());
                view.getContext().startActivity(to_activity_info);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActivityItem activity = activityList.get(position);
        holder.activityImage.setImageResource(activity.getImage());
        holder.activityName.setText(activity.getName());
        holder.activityID.setText(activity.getId());
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

}

