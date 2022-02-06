package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText submit_DoB;
    private EditText submit_Email;
    private Button btn_submit;

    private String str1;
    private String str2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        submit_DoB=findViewById(R.id.submit_DoB);
        submit_Email=findViewById(R.id.submit_Email);
        btn_submit=findViewById(R.id.btn_submit);
        /*
        서버:
        이메일과 생년월일을 서버에 제출하면, 서버는 사용자의 현재 비밀번호를 임시 비밀번호로 수정하고 사용자 이메일로 임시 비밀번호를 발송해준다.
         */
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str1=submit_DoB.getText().toString();
                str2=submit_Email.getText().toString();


            }
        });
    }
}