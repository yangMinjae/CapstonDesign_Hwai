package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Main_loginActivity extends AppCompatActivity {
    private TextInputEditText email; // 로그인 화면에서 Email 입력하는 Edittext
    private TextInputEditText pw; // 로그인 화면에서 Password 입력하는 Edittext
    private Button btn_enter; // Login 버튼입니다.
    private TextView tv_forgotPassword; // Login 버튼 하단의 Forgot Password? Textview입니다.
    private TextView tv_createAccount; // Login 버튼 하단의 createAccount? Textview입니다.
    private Boolean if_admin = false; // 로그인 버튼을 누른 후 서버로 부터 전달받게되는 admin/genereal_user여부
                                     // admin이면 true, general_user면 false

    // 위의 if_admin 부분을 로그인 시 스위치를 통해 사용자가 지정하도록 하는게 나을 것 같아서 아래와 같이 admin_status을 추가하였습니다.
    private Switch admin_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        email=findViewById(R.id.email);
        pw=findViewById(R.id.pw);
        btn_enter=findViewById(R.id.btn_enter);
        tv_forgotPassword=findViewById(R.id.tv_forgotPassword);
        tv_createAccount=findViewById(R.id.tv_createAccount);
        admin_switch=findViewById(R.id.admin_switch);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Main_loginActivity.this, GeneralLoginActivity.class);
                Intent intent2 = new Intent(Main_loginActivity.this, AdminLoginActivity.class);
                if (if_admin) {
                    startActivity(intent2);
                }
                else if (!if_admin){
                    startActivity(intent1);
                }
            }
        });
        admin_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b){
                if (b){
                    if_admin = true;
                }
                else {
                    if_admin = false;
                }
            }
        });
        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_loginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        tv_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_loginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}