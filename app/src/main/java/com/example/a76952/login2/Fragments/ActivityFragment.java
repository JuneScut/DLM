package com.example.a76952.login2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.a76952.login2.ActivityDetail;
import com.example.a76952.login2.ActivityItemAdapter;
import com.example.a76952.login2.MainActivity;
import com.example.a76952.login2.R;

import java.util.ArrayList;
import java.util.List;

import com.example.a76952.login2.ActivityItem;

/**
 * Created by 76952 on 2018/8/11.
 */

public class ActivityFragment extends Fragment {
   // private String[] data={"apple","banana","orange","watamelon","pear","grape"};
    private List<ActivityItem> activityItemList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_page_layout,container,false);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
        initActivity();
        ActivityItemAdapter adapter=new ActivityItemAdapter(getActivity(),R.layout.activity_item,activityItemList);
        ListView listView=(ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityItem item=activityItemList.get(position);
               // Toast.makeText(getActivity(),item.getActivityName(),Toast.LENGTH_SHORT).show();
                String activityName=item.getActivityName();
                String activityTime=item.getActivityTime();
                Intent intent=new Intent(getActivity(), ActivityDetail.class);
                intent.putExtra("activityName",activityName);
                intent.putExtra("activityTime",activityTime);
                startActivity(intent);

            }
        });
        return view;
    }
    private void initActivity(){
        ActivityItem acti1=new ActivityItem("activity1","time1");
        activityItemList.add(acti1);
        ActivityItem acti2=new ActivityItem("activity2","time2");
        activityItemList.add(acti2);
        ActivityItem acti3=new ActivityItem("activity3","time3");
        activityItemList.add(acti3);
        ActivityItem acti4=new ActivityItem("activity4","time4");
        activityItemList.add(acti4);
        ActivityItem acti5=new ActivityItem("activity5","time5");
        activityItemList.add(acti5);
        ActivityItem acti6=new ActivityItem("activity6","time6");
        activityItemList.add(acti6);
        ActivityItem acti7=new ActivityItem("activity7","time7");
        activityItemList.add(acti7);
        ActivityItem acti8=new ActivityItem("activity8","time8");
        activityItemList.add(acti8);
        ActivityItem acti9=new ActivityItem("activity9","time9");
        activityItemList.add(acti9);
        ActivityItem acti10=new ActivityItem("activity10","time10");
        activityItemList.add(acti10);
    }
}
