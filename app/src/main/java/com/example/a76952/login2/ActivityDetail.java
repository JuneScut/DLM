package com.example.a76952.login2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a76952.login2.Objects.Cur;
import com.example.a76952.login2.network.HttpCallBackListener;
import com.example.a76952.login2.network.HttpConnect;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityDetail extends AppCompatActivity  implements View.OnClickListener {
//    private TextView actiName;
//    private TextView actiTime;
//    private TextView actiPlace;
//    private TextView actiSponsor;
//    private TextView actiMonitor;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//        actiName=(TextView)findViewById(R.id.activity_name);
//        actiTime=(TextView)findViewById(R.id.activity_time);
//        actiPlace=(TextView) findViewById(R.id.activity_place);
//        actiSponsor=(TextView)findViewById(R.id.activity_sponsor);
//        actiMonitor=(TextView)findViewById(R.id.activity_monitor);
//        Intent intent=getIntent();
//        String activityName=intent.getStringExtra("activityName");
//        String activityTime=intent.getStringExtra("activityTime");
//        actiName.setText(activityName);
//        actiTime.setText(activityTime);
//        actiPlace.setText("xxx");
//        actiSponsor.setText("xxx");
//        actiMonitor.setText("xxx");
//    }
private TextView  textview_name;
    private ImageView imageview_image;
    private TextView  textview_time;
    private TextView  textview_place;
    private Button    button_sign;
    private Button button_invite;
    private Button    button_delete;
    private TextView  textview_info;
    private String id;
    private String creater;
    private String address ="http://biketomotor.cn:3000/api/GetActivityDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textview_name   = (TextView)findViewById(R.id.layout_activity_info_name);
        imageview_image = (ImageView)findViewById(R.id.layout_activity_info_image);
        textview_time   = (TextView)findViewById(R.id.layout_activity_info_time);
        textview_place  = (TextView)findViewById(R.id.layout_activity_info_place);
        button_sign     = (Button)findViewById(R.id.layout_activity_info_sign);
        button_invite   = (Button)findViewById(R.id.layout_activity_info_invite);
        button_delete   = (Button)findViewById(R.id.layout_activity_info_delete);
        textview_info   = (TextView)findViewById(R.id.layout_activity_info_info);

        button_sign.setOnClickListener(this);
        button_delete.setOnClickListener(this);
        button_invite.setOnClickListener(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        creater = intent.getStringExtra("creater");
        System.out.println("Cur.getAccount().toString()");
        System.out.println(Cur.getAccount().toString());
        //这里会报错，不能把compareTo指向空指针
//        if (creater.compareTo(Cur.getAccount().toString()) != 0) {
//            button_delete.setVisibility(View.GONE);
//            button_invite.setVisibility(View.GONE);
//        }
// 把活动的creator 字段和存储在本地的userId做比较 相同的话需要增加 “删除活动” “邀请”
// 邀请的话需要知道被邀请者的id
// 加一个签到按钮
        JSONObject jsonData = getJson();
        HttpConnect.sendHttpRequest(address, "POST", jsonData, new HttpCallBackListener() {
            @Override
            public void success(String response) {
                catchResponse(response);
            }

            @Override
            public void error(Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private JSONObject getJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void catchResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("result");
                    String reason = json.getString("reason");
                    if (result.compareTo("true") == 0) {
                        String name = json.getString("activity_name");
                        String time = json.getString("activity_time");
                        String place = json.getString("activity_place");
                        String creater = json.getString("creater");
                        CharSequence sysTimeStr = DateFormat.format("yyyy年MM月dd日hh时mm分", Long.parseLong(time));//时间显示格式
                        textview_name.setText(name);
                        textview_time.setText(sysTimeStr);
                        textview_place.setText(place);
                    } else {
                        Toast.makeText(ActivityDetail.this, reason, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_activity_info_sign:
                break;
            case R.id.layout_activity_info_invite:
                Intent to_invite = new Intent(ActivityDetail.this, ActivityInvite.class);
                to_invite.putExtra("id", id);
                startActivity(to_invite);
                break;
            case R.id.layout_activity_info_delete:
                String del_address = "http://biketomotor.cn:3000/api/DeleteActivity";
                JSONObject delJson = getDelJson();
                HttpConnect.sendHttpRequest(del_address, "POST", delJson, new HttpCallBackListener() {
                    @Override
                    public void success(String response) {
                        finish();
                    }

                    @Override
                    public void error(Exception exception) {
                        exception.printStackTrace();
                    }
                });
                break;
        }
    }

    private JSONObject getDelJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private JSONObject getIvtJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
