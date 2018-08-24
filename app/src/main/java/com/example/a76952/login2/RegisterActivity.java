package com.example.a76952.login2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a76952.login2.Objects.User;
import com.example.a76952.login2.network.HttpCallBackListener;
import com.example.a76952.login2.network.HttpConnect;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText edittext_nickname;
    private EditText edittext_account;
    private EditText edittext_tel;
    private EditText edittext_password1;
    private EditText edittext_password2;

    private String nickname;
    private String account;
    private String tel;
    private String password1;
    private String password2;

    private User user;
    private final String api = "http://biketomotor.cn:3000/api/UserSignUp";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton=findViewById(R.id.btn_regisiter);
        edittext_nickname=findViewById(R.id.et_userName);
        edittext_account=findViewById(R.id.et_IdNumber);
        edittext_tel=findViewById(R.id.et_phoneNumber);
        edittext_password1=findViewById(R.id.et_password);
        edittext_password2=findViewById(R.id.et_recongnizePwd);


        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                nickname  = edittext_nickname.getText().toString();
                account   = edittext_account.getText().toString();
                tel       = edittext_tel.getText().toString();
                password1 = edittext_password1.getText().toString();
                password2 = edittext_password2.getText().toString();
                if(!isValidRegister(nickname,account,tel,password1,password2)){
                    return;
                }
                else if(!isMobileNO(tel)){
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }
                else if (password1.compareTo(password2) == 0) {
                    user = new User(account,password1, tel, nickname);
                    JSONObject jsonData = getJson();

                    /* Use HttpUrlConnection */
                    HttpConnect.sendHttpRequest(api, "POST", jsonData, new HttpCallBackListener() {
                        @Override
                        public void success(String response) {
                            catchResponse(response);
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void error(Exception exception) {
                            exception.printStackTrace();
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致，请重新确认你的密码", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private JSONObject getJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("account", account);
            json.put("pwd", password1);
            json.put("telnumber", tel);
            json.put("name", nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void catchResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(activity_register.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonData = new JSONObject(response);
                    String result = jsonData.getString("result");
                    String reason = jsonData.getString("reason");
                    if (result.compareTo("true") == 0) {
                        Toast.makeText(RegisterActivity.this, reason, Toast.LENGTH_SHORT).show();
//                        Intent to_login = new Intent(activity_register.this, activity_login.class);
//                        startActivity(to_login);
//                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean isValidRegister(String name,String account,String phone,String pwd1,String pwd2) {
        // 用户名和密码不得为空
        if (name.trim().equals("")) {
            Toast.makeText(this,"用户名不得为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (account.trim().equals("")) {
            Toast.makeText(this,"学号不得为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (phone.trim().equals("")) {
            Toast.makeText(this,"手机号码不得为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (pwd1.trim().equals("")) {
            Toast.makeText(this,"密码不得为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (pwd2.trim().equals("")) {
            Toast.makeText(this,"请再次确认你的密码",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public static boolean isMobileNO(String mobiles) {
        //验证手机号码
    /*
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
        String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return mobiles.matches(telRegex);
    }
}
