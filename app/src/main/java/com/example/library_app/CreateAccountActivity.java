package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
                if(str1.length()>=2 && str2.length()>=6 && str3.length()>=11 && str4.length()>=9 && str5.length()>=6){  // 각 Edittext가 일정 길이 이상 채워져 있을때만 제출 가능
                    /*
                    서버:
                    서버에 사용자 정보 제출
                     */

                    Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CreateAccountActivity.this, Main_loginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"회원가입 정보를 제대로 입력해 주세요.",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}