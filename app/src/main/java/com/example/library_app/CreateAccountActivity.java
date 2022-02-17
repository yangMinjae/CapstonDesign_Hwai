package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText submit_Name;       //이름
    private EditText submit_DoB;        //생년월일
    private EditText submit_PhoneNum;   //폰번호
    private EditText submit_Email;      //Email
    private EditText submit_PW;         //Password
    private CheckBox checkbox_ifadmin;  //checkbox

    private String str1;                //이름
    private String str2;                //생년월일
    private String str3;                //폰번호
    private String str4;                //Email
    private String str5;                //Password

    private Boolean admin;

    private final String joinurl="users/join";
    private String rtnd_res;
    private final int joinRet=201;

    private Button btn_submit;          //제출버튼
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        submit_Name=findViewById(R.id.submit_Name);
        submit_DoB=findViewById(R.id.submit_DoB);
        submit_PhoneNum=findViewById(R.id.submit_PhoneNum);
        submit_Email=findViewById(R.id.submit_Email);
        submit_PW=findViewById(R.id.submit_PW);

        checkbox_ifadmin=findViewById(R.id.checkbox_ifadmin);

        btn_submit=findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str1=submit_Name.getText().toString();
                str2=submit_DoB.getText().toString();
                str3=submit_PhoneNum.getText().toString();
                str4=submit_Email.getText().toString();
                str5=submit_PW.getText().toString();        // 버튼 클릭시 Edittext의 text를 가져옴

                admin = checkbox_ifadmin.isChecked() ? true : false;
                str3=telParse(str3);

                if(str1.length()>=2 && str2.length()>=6 && str3.length()>=13 && str4.length()>=9 && str5.length()>=4){  // 각 Edittext가 일정 길이 이상 채워져 있을때만 제출 가능

                    try{
                        String json = JsonString(str1,str2,str3,str4,str5,admin);
                        ServerTask_post task = new ServerTask_post(joinurl, joinRet);
                        task.execute(json);
                        rtnd_res= task.get();
                        int rtndStatus = task.rtndStatus;
                        Log.d("test1",""+rtndStatus);
                        Log.d("test2",""+rtnd_res);

                        if(rtndStatus==joinRet){
                            Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"중복된 이메일이 있습니다.\n기존 회원인지 확인해주세요.",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"회원가입 정보를 제대로 입력해 주세요.",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private String JsonString(String name, String birth, String tel, String email, String pw, Boolean admin) throws Exception{    // email과 pw를 jsonstring으로 변환하기위한 함수
        // 변환후, ServerTask_post.execute()의 인자로 사용됨
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("name", name);
        param.put("birth", birth);
        param.put("tel", tel);
        param.put("email", email);
        param.put("pw", pw);
        param.put("admin", admin);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(param);
        return json;
    }
    private String telParse(String tel){
        String tel1;
        String tel2;
        String tel3;
        tel1=tel.substring(0,3);
        tel2=tel.substring(3,7);
        tel3=tel.substring(7);
        return tel1+"-"+tel2+"-"+tel3;
    }
}