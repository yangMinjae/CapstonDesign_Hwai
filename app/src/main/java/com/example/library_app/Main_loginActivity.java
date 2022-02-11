package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Main_loginActivity extends AppCompatActivity {
    private TextInputEditText login_email; // 로그인 화면에서 Email 입력하는 Edittext
    private TextInputEditText login_pw; // 로그인 화면에서 Password 입력하는 Edittext
    private Button btn_enter; // Login 버튼입니다.
    private TextView tv_forgotPassword; // Login 버튼 하단의 Forgot Password? Textview입니다.
    private TextView tv_createAccount;  // Login 버튼 하단의 createAccount? Textview입니다.

    private String loginUrl="users/login";

    private String rtnd_res;
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

                    try{

                        String json = JsonString(str1, str2);
                        ServerTask_post task = new ServerTask_post(loginUrl);
                        task.execute(json);
                        rtnd_res= task.get();
                        int status = task.status;
                        Log.d("test1",""+status);
                        Log.d("test2",""+rtnd_res);

                        if (status==200){   //관리자 로그인 (서버는 로그인 성공시 status 200 을 리턴)
                            LoginParse myInfo = LoginParsing(rtnd_res);
                            if(myInfo.getAdmin()){
                                intent2.putExtra("id",myInfo.getId());
                                intent2.putExtra("name",myInfo.getName());
                                intent2.putExtra("pinNum",myInfo.getPinNum());  //다음 인텐트로 정보 넘기기
                                startActivity(intent2);
                            }else {     //일반 로그인
                                intent1.putExtra("id",myInfo.getId());          //다음 인텐트로 정보 넘기기
                                intent1.putExtra("name",myInfo.getName());
                                startActivity(intent1);
                            }
                        } else {    //로그인 실패시 토스트 메시지 출력
                            login_email.setText("");
                            login_pw.setText("");
                            login_email.requestFocus();
                            Toast.makeText(getApplicationContext(), "올바르지 않은 회원정보입니다.\nEmail과 Password를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
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
    private String JsonString(String email, String pw) throws Exception{    // email과 pw를 jsonstring으로 변환하기위한 함수
                                                                            // 변환후, ServerTask_post.execute()의 인자로 사용됨
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("email",email);
        param.put("pw",pw);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(param);
        return json;
    }
    private LoginParse LoginParsing(String json){       // 로그인 성공시 서버로 부터 받아온 정보를 LoginParse클래스에 저장 후 리턴
        try{
            JSONObject jsonObject = new JSONObject(json);
            LoginParse myInfo = new LoginParse();

            myInfo.setId(jsonObject.getInt("id"));
            myInfo.setName(jsonObject.getString("name"));
            myInfo.setAdmin(jsonObject.getBoolean("admin"));
            myInfo.setPinNum(jsonObject.getInt("pinNum"));

            return myInfo;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}