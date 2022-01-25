package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText submit_Name;
    private EditText submit_Email;
    private Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
        서버:
        이메일과 사용자 이름을 서버에 제출하면, 서버는 사용자의 현재 비밀번호를 임시 비밀번호로 수정하고 사용자 이메일로 임시 비밀번호를 발송해준다.
         */
    }
}