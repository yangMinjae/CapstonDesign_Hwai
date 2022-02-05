package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;


public class Main_loginActivity extends AppCompatActivity {
    private TextInputEditText login_email; // 로그인 화면에서 Email 입력하는 Edittext
    private TextInputEditText login_pw; // 로그인 화면에서 Password 입력하는 Edittext
    private Button btn_enter; // Login 버튼입니다.
    private TextView tv_forgotPassword; // Login 버튼 하단의 Forgot Password? Textview입니다.
    private TextView tv_createAccount;  // Login 버튼 하단의 createAccount? Textview입니다.
    private Boolean if_admin = false;   // 로그인 버튼을 누른 후 서버로 부터 전달받게되는 admin/genereal_user여부
                                        // admin이면 true, general_user면 false
    private Boolean if_member = true;  // 서버로부터 받아오는 회원인지 여부(잘못된 Email, password 면 로그인 서버는 false리턴)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        login_email=findViewById(R.id.login_email);
        login_pw=findViewById(R.id.login_pw);
        btn_enter=findViewById(R.id.btn_enter);
        tv_forgotPassword=findViewById(R.id.tv_forgotPassword);
        tv_createAccount=findViewById(R.id.tv_createAccount);


        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Main_loginActivity.this, GeneralLoginActivity.class);
                Intent intent2 = new Intent(Main_loginActivity.this, AdminLoginActivity.class);

                String str1 = login_email.getText().toString();
                String str2 = login_pw.getText().toString();

                if(Cantsubmit(str1, str2)){    // email 또는 pw가 아무글자도 입력되지않았을때 토스트 메시지
                    Toast.makeText(getApplicationContext(), "Email과 Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{

//                    try{
//                        HashMap<String, String> param = new HashMap<String, String>();
//                        param.put("email",str1);
//                        param.put("pw",str2);
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        String json = objectMapper.writeValueAsString(param);
//                        new ServerTask().execute(json);
//                        Toast.makeText(getApplicationContext(), "서버전송 완료", Toast.LENGTH_SHORT).show();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }

                    if(if_member){
                        if (if_admin) {
                            startActivity(intent2);
                        }
                        else if (!if_admin){
                            startActivity(intent1);
                        }
                    }
                    else{
                        login_email.setText("");
                        login_pw.setText("");
                        Toast.makeText(getApplicationContext(), "잘못된 회원정보 입니다.", Toast.LENGTH_SHORT).show();
                    }
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
    private Boolean Cantsubmit(String email, String pw){   //email 또는 pw가 아무글자도 입력되지않았을때 true 리턴
        return email.length()==0 || pw.length()==0;
    }
}