package com.example.a76952.login2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a76952.login2.network.HttpCallBackListener;
import com.example.a76952.login2.network.HttpConnect;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 76952 on 2018/9/5.
 */

public class ActivityInvite extends AppCompatActivity implements View.OnClickListener {
    private EditText et_account;
    private Button bt_invite;
    private String account;
    private String id;
    private String address = "http://biketomotor.cn:3000/api/InviteUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        et_account = (EditText)findViewById(R.id.layout_invite_account);
        bt_invite = (Button)findViewById(R.id.layout_invite_invite);

        bt_invite.setOnClickListener(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id").toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_invite_invite:
                account = et_account.getText().toString();
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
                break;
        }
    }

    private JSONObject getJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("participant", account);
            json.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    private void catchResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            System.out.println("response");
            System.out.println(response);
            try{
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if (result.compareTo("true") == 0) {
                    Toast.makeText(ActivityInvite.this, "邀请成功", Toast.LENGTH_SHORT).show();
                    Intent to_detail = new Intent(ActivityInvite.this, ActivityDetail.class);
                    startActivity(to_detail);
                } else {
                    String reason = json.getString("reason");
                    Toast.makeText(ActivityInvite.this, "邀请失败"+reason, Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
                }
            });
    }
}
