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


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUserName;
    private EditText etUserPassword;
    private Button btnLogin;
    private String userName;
    private String userPassword;
    private Handler mhandler;
    private TextView registerText;

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
        EditText userName = (EditText) findViewById(R.id.et_userName);
        EditText password = (EditText) findViewById(R.id.et_password);
        ImageView unameClear = (ImageView) findViewById(R.id.iv_unameClear);
        ImageView pwdClear = (ImageView) findViewById(R.id.iv_pwdClear);

        EditTextClearTools.addClearListener(userName,unameClear);
        EditTextClearTools.addClearListener(password,pwdClear);
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
        //实现界面的跳转
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        //关闭当前界面
        finish();

    }

    public boolean isUserNameAndPwdValid() {
        // 用户名和密码不得为空
        if (etUserName.getText().toString().trim().equals("")) {
            Toast.makeText(this,"用户名不得为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (etUserPassword.getText().toString().trim().equals("")) {
            Toast.makeText(this,"密码不得为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
