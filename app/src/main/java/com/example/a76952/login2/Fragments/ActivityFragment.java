package com.example.a76952.login2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.a76952.login2.ActivityDetail;
import com.example.a76952.login2.ActivityItemAdapter;
import com.example.a76952.login2.MainActivity;
import com.example.a76952.login2.Objects.Cur;
import com.example.a76952.login2.R;

import java.util.ArrayList;
import java.util.List;

import com.example.a76952.login2.ActivityItem;
import com.example.a76952.login2.network.HttpCallBackListener;
import com.example.a76952.login2.network.HttpConnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 76952 on 2018/8/11.
 */

public class ActivityFragment extends Fragment {
   // private String[] data={"apple","banana","orange","watamelon","pear","grape"};
    //private List<ActivityItem> activityItemList=new ArrayList<>();
    private List<ActivityItem> activityList=new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Context mContext;//传递活动
    private String api = "http://biketomotor.cn:3000/api/GetParticipantActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_page_layout,container,false);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
        mContext=getActivity();
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle_view);

        //拿到所有参加的活动
        JSONObject jsonData = getJson();
        HttpConnect.sendHttpRequest(api, "POST", jsonData, new HttpCallBackListener() {
            @Override
            public void success(String response) {
                catchResponse(response);
                System.out.println(response);
            }

            @Override
            public void error(Exception exception) {
                exception.printStackTrace();
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ActivityItem item=activityItemList.get(position);
//               // Toast.makeText(getActivity(),item.getActivityName(),Toast.LENGTH_SHORT).show();
//                String activityName=item.getActivityName();
//                String activityTime=item.getActivityTime();
//                Intent intent=new Intent(getActivity(), ActivityDetail.class);
//                intent.putExtra("activityName",activityName);
//                intent.putExtra("activityTime",activityTime);
//                startActivity(intent);
//
//            }
//        });
        return view;
    }
    private JSONObject getJson() {
        JSONObject json = new JSONObject();
        String participant = Cur.getAccount();
        try {
            json.put("participant", participant);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    private void catchResponse(final String response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("result");
                    String reason = json.getString("reason");
                    String acts = json.getString("activity");
                    if (result.compareTo("true") == 0) {
                        JSONArray jsonArray = new JSONArray(acts);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //Toast.makeText(mContext, String.valueOf(i), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("activity_name").toString();
                            String id = jsonObject.getString("activity_id").toString();
                            String creater = jsonObject.getString("creater");
                            activityList.add(new ActivityItem(R.drawable.ic_rightfoot, name, id, creater));
                        }
                        System.out.println("activityList");
                        System.out.println(activityList);

                        //设置布局管理器
                        layoutManager=new LinearLayoutManager(mContext);
                        recyclerView.setLayoutManager(layoutManager);
                        ActivityItemAdapter adapter = new ActivityItemAdapter(activityList);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
