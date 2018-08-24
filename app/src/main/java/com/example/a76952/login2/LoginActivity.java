package com.example.a76952.login2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a76952.login2.Objects.Cur;
import com.example.a76952.login2.network.HttpCallBackListener;
import com.example.a76952.login2.network.HttpConnect;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUserName;
    private EditText etUserPassword;
    private Button btnLogin;
    private String userName;
    private String userPassword;
    private Handler mhandler;
    private TextView registerText;
    private final String api= "http://biketomotor.cn:3000/api/UserSignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_activity);
        init();
        btnLogin=(Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        registerText=(TextView)findViewById(R.id.register);
        registerText.setOnClickListener(this);
    }

    private void init(){
        etUserName = (EditText) findViewById(R.id.et_userName);
        etUserPassword = (EditText) findViewById(R.id.et_password);
//        ImageView unameClear = (ImageView) findViewById(R.id.iv_unameClear);
//        ImageView pwdClear = (ImageView) findViewById(R.id.iv_pwdClear);
//        EditTextClearTools.addClearListener(userName,unameClear);
//        EditTextClearTools.addClearListener(password,pwdClear);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        Log.d("LoginActivity","已经提交");
    }
    public void login(){
        userName=etUserName.getText().toString();
        userPassword=etUserPassword.getText().toString();
        Log.d("userName",userName);
        Log.d("password",userPassword);
        if(!isUserNameAndPwdValid(userName,userPassword)){
            return;
        }
        else{
            JSONObject jsonData = getJson(userName,userPassword);
             /* Use HttpUrlConnection  发送登录请求 用户名是学号 */
            HttpConnect.sendHttpRequest(api, "POST", jsonData, new HttpCallBackListener() {
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
    }

    public boolean isUserNameAndPwdValid(String name,String pwd) {
        // 用户名和密码不得为空
        if (name.trim().equals("")) {
            Toast.makeText(this,"用户名不得为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (pwd.trim().equals("")) {
            Toast.makeText(this,"密码不得为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private JSONObject getJson(String account,String pwd) {
        JSONObject json = new JSONObject();
        try {
            json.put("account", account);
            json.put("pwd", pwd);
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
                    JSONObject jsonData = new JSONObject(response);
                    String result = jsonData.getString("result");
                    String reason = jsonData.getString("reason");
                    if (result.compareTo("true") == 0) {
                        Cur.setAccount(etUserName.getText().toString());
                        //Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();
                        //实现界面的跳转
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        //关闭当前界面
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
