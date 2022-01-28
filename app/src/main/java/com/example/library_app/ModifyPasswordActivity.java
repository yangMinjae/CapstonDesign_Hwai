package com.example.library_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyPasswordActivity extends AppCompatActivity {
    private EditText Current_Name;       //이름
    private EditText Current_DoB;        //생년월일
    private EditText Current_PhoneNum;   //폰번호
    private EditText Current_Email;      //Email
    private EditText Current_PW;         //Password
    private TextView admin_OX;           //관리자 여부

    private Boolean ifadmin= true;        //서버로 부터 받아오는 if admin값(true면 관리자)

    private String str1;                //이름
    private String str2;                //생년월일
    private String str3;                //폰번호
    private String str4;                //Email
    private String str5;                //현재 PW값

    private String str6;                //관리자/일반

    private String str7;                //서버에 제출할 변경된 pw String값

    private Button btn_submit;          //제출버튼
    
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


        str1="홍길동";             //이 부분은 서버 완성후 아래코드로 대체
        str2="18970504";          //이 부분은 서버 완성후 아래코드로 대체
        str3="01011111111";       //이 부분은 서버 완성후 아래코드로 대체
        str4="Email@naver.com";   //이 부분은 서버 완성후 아래코드로 대체
        str5="PW1234";            //이 부분은 서버 완성후 아래코드로 대체

        /*
        서버:
        str1= 서버에서 받아온 이름
        str2= 서버에서 받아온 생년월일
        str3= 서버에서 받아온 전화번호
        str4= 서버에서 받아온 Email
        str5= 서버에서 받아온 현재 Password

        ifadmin= 서버에서 받아온 if admin값(true/false)
        */

        str6=(ifadmin)? "관리자":"일반";

        Current_Name.setText(str1);
        Current_DoB.setText(str2);
        Current_PhoneNum.setText(str3);
        Current_Email.setText(str4);
        Current_PW.setText(str5);

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
                /*
                서버:
                서버에 변경된 Password값(str7) 제출
                 */
                Toast.makeText(getApplicationContext(),"비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}