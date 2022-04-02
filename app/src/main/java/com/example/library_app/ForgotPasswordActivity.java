package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText submit_Email;
    private Button btn_submit;

    private String str;

    private final String forgotUrl="users/sendEmail";
    private final int forgotRet=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        submit_Email=findViewById(R.id.submitF_Email);

        btn_submit=findViewById(R.id.btnF_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str=submit_Email.getText().toString();
                try {
                    String json = JsonString(str);
                    ServerTask_post task = new ServerTask_post(forgotUrl, forgotRet);
                    task.execute(json);
                    String rtnd_res= task.get();
                    int rtndStatus = task.rtndStatus;
                    Log.d("aaaaa",""+rtndStatus);
                    Log.d("aaaaa",""+rtnd_res);

                    if(rtndStatus==forgotRet){
                        String right_email=submit_Email.getText().toString();
                        Toast.makeText(getApplicationContext(),right_email+"\n주소로 변경된 비밀번호가 전송되었습니다.",Toast.LENGTH_LONG);


                    } else{
                        submit_Email.setText("");
                        Toast.makeText(getApplicationContext(),"등록되지 않은 사용자 입니다.\n회원정보를 다시 확인해 주세요",Toast.LENGTH_LONG).show();
                    }




                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });
    }
    private String JsonString( String email ) throws Exception{    // email과 pw를 jsonstring으로 변환하기위한 함수
        // 변환후, ServerTask_post.execute()의 인자로 사용됨
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("userEmail",email);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(param);
        return json;
    }
}