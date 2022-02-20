package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ModifyPasswordActivity extends AppCompatActivity {
    private EditText Current_Name;       //이름
    private EditText Current_DoB;        //생년월일
    private EditText Current_PhoneNum;   //폰번호
    private EditText Current_Email;      //Email
    private EditText Current_PW;         //Password
    private TextView admin_OX;

    private Boolean admin;        //서버로 부터 받아오는 admin값(true면 관리자)

    private String str1;                //이름
    private String str2;                //생년월일
    private String str3;                //폰번호
    private String str4;                //Email
    private String str5;                //현재 PW값

    private String str6;                //관리자/일반

    private String str7;                //서버에 제출할 변경된 pw String값

    private Button btn_submit;          //제출버튼

    private String modify1Url="users/page/";
    private final int modify1Ret=200;

    private final String modify2Url="users/pw";
    private final int modify2Ret=204;

    private String rtnd_res;
    private int id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Current_Name=findViewById(R.id.Current_Name);
        Current_DoB=findViewById(R.id.Current_DoB);
        Current_PhoneNum=findViewById(R.id.Current_PhoneNum);
        Current_Email=findViewById(R.id.Current_Email);
        Current_PW=findViewById(R.id.Current_PW);
        admin_OX=findViewById(R.id.admin_OX);
        btn_submit=findViewById(R.id.btn_submit);

        Intent intent=getIntent();
        admin=intent.getBooleanExtra("admin",false);
        id=intent.getIntExtra("id",-1);
        modify1Url+=""+id;

        try {
            ServerTask_get task = new ServerTask_get(modify1Url, modify1Ret);
            task.execute();
            rtnd_res= task.get();
            int rtndStatus = task.rtndStatus;
            if(rtndStatus==modify1Ret){
                MyInfo myInfo = M_Parsing(rtnd_res);
                str1=myInfo.getName();
                str2=myInfo.getBirth();
                str3=myInfo.getTel();
                str4=myInfo.getEmail();
                str5=myInfo.getPw();
            }else {
                Toast.makeText(getApplicationContext(),"회원정보 조회 실패",Toast.LENGTH_SHORT);
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        str6=(admin)? "관리자":"일반";

        Current_Name.setText(str1);
        Current_DoB.setText(str2);
        Current_PhoneNum.setText(str3);
        Current_Email.setText(str4);
        Current_PW.setText(str5);
        admin_OX.setText(str6);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str7=Current_PW.getText().toString();
                if(str7.length()<6){
                    Toast.makeText(getApplicationContext(),"비밀번호는 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str7.equals(str5)){
                    Toast.makeText(getApplicationContext(),"비밀번호가 이전과 같습니다.\n변경해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String json = JsonString(id,str7);
                    ServerTask_patch task = new ServerTask_patch(modify2Url, modify2Ret);
                    task.execute(json);
                    rtnd_res= task.get();
                    int rtndStatus = task.rtndStatus;
                    Log.d("testM",rtnd_res);
                    Log.d("testM",""+rtndStatus);
                    if (rtndStatus==modify2Ret){
                        Toast.makeText(getApplicationContext(),"비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"오류감지", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


    }
    private MyInfo M_Parsing(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            MyInfo myInfo = new MyInfo();
            myInfo.setName(jsonObject.getString("name"));
            myInfo.setBirth(jsonObject.getString("birth"));
            myInfo.setTel(jsonObject.getString("tel"));
            myInfo.setEmail(jsonObject.getString("email"));
            myInfo.setPw(jsonObject.getString("pw"));

            return myInfo;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    private String JsonString(int id,String new_pw) throws Exception{
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("id",id);
        param.put("new_pw",new_pw);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(param);
        return json;
    }
}