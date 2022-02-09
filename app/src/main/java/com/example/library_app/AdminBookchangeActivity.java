package com.example.library_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AdminBookchangeActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    String[] categories;

    private Button btn_chg;             // 카테고리 변경 버튼
    private Button btn_submitCategory;  //서버에 제출 버튼
    private TextView category1;         // 1층의 카테고리
    private TextView category2;         // 2층의 카테고리
    private TextView category3;         // 3층의 카테고리

    private LinearLayout cb_list_1f;
    private LinearLayout cb_list_2f;
    private LinearLayout cb_list_3f;

    private CheckBox[][] checkBoxes;

    private String updated_category1;
    private String updated_category2;
    private String updated_category3;



    private Boolean pass;

    private void check_category(CheckBox[] cb, TextView tv) {
        tv.setText("");
        for (int i = 0; i < categories.length; i++) {
            if (cb[i].isChecked()) {
                tv.append(categories[i] + ", ");
            }
        }

        if (!tv.getText().toString().equals("")) {
            tv.setText(tv.getText().toString().substring(0, tv.getText().toString().length() - 2));
        }
    }

    private void check_three(CheckBox[][] cb) {
        int cnt = 0;
        for (int i = 0; i < cb.length; i++) {
            for (int j = 0; j < cb[0].length; j++) {
                if (cb[i][j].isChecked()) {
                    cnt++;
                }
            }
            if (cnt >= 3) {
                for (int j = 0; j < cb[0].length; j++) {
                    if (!cb[i][j].isChecked()){
                        cb[i][j].setEnabled(false);
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_bookchange);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        categories=getResources().getStringArray(R.array.book_category); // 카테고리 배열 string.xml에서 추가 및 삭제

        btn_chg = findViewById(R.id.btn_chg);
        category1 = findViewById(R.id.category1);
        category2 = findViewById(R.id.category2);
        category3 = findViewById(R.id.category3);
        btn_submitCategory = findViewById(R.id.btn_submitCategory);

        cb_list_1f = findViewById(R.id.cb_list_1f);
        cb_list_2f = findViewById(R.id.cb_list_2f);
        cb_list_3f = findViewById(R.id.cb_list_3f);

        pass = true;

        checkBoxes = new CheckBox[][]{{findViewById(R.id.cb_novel_1f), findViewById(R.id.cb_science_1f), findViewById(R.id.cb_pilosophy_1f), findViewById(R.id.cb_literature_1f), findViewById(R.id.cb_assay_1f)},
                {findViewById(R.id.cb_novel_2f), findViewById(R.id.cb_science_2f), findViewById(R.id.cb_pilosophy_2f), findViewById(R.id.cb_literature_2f), findViewById(R.id.cb_assay_2f)},
                {findViewById(R.id.cb_novel_3f), findViewById(R.id.cb_science_3f), findViewById(R.id.cb_pilosophy_3f), findViewById(R.id.cb_literature_3f), findViewById(R.id.cb_assay_3f)}};
        /*
        서버:
        updated_category1= 서버에서 가져온 카테고리 정보 저장;
        updated_category2= 서버에서 가져온 카테고리 정보 저장;
        updated_category3= 서버에서 가져온 카테고리 정보 저장;

        category1.setText(updated_category1);
        category2.setText(updated_category2);
        category3.setText(updated_category3);
        */

        View.OnClickListener chg_btn_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_chg.getText().toString().equals("변경")) {
                    btn_submitCategory.setEnabled(false);
                    btn_chg.setText("확인");

                    cb_list_1f.setVisibility(View.VISIBLE);
                    cb_list_2f.setVisibility(View.VISIBLE);
                    cb_list_3f.setVisibility(View.VISIBLE);

                }
                else {
                    btn_submitCategory.setEnabled(true);
                    check_category(checkBoxes[0], category1);
                    check_category(checkBoxes[1], category2);
                    check_category(checkBoxes[2], category3);

                    for (int i = 0; i < checkBoxes.length; i++) {
                        pass = false;
                        for (int j = 0; j < checkBoxes[0].length; j++) {
                            if (checkBoxes[i][j].isChecked()) {
                                pass = true;
                                break;
                            }
                        }
                        if (!pass) {
                            break;
                        }
                    }

                    if (!pass) {
                        Toast.makeText(getApplicationContext(), "층마다 적어도 하나의 카테고리를 선택하세요", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        btn_chg.setText("변경");

                        cb_list_1f.setVisibility(View.GONE);
                        cb_list_2f.setVisibility(View.GONE);
                        cb_list_3f.setVisibility(View.GONE);

                    }



                }

            }
        };


        btn_chg.setOnClickListener(chg_btn_listener);
        for(int i =0; i<checkBoxes.length;i++){
            for(int j = 0; j<checkBoxes[i].length;j++){
                checkBoxes[i][j].setOnCheckedChangeListener(this);
            }
        }
        btn_submitCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                서버:
                서버로 변경된 카테고리 전송
                 */
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        String self = compoundButton.getText().toString();
        if(compoundButton.isChecked()){
            for(int i =0; i<checkBoxes.length;i++){
                for(int j = 0; j<checkBoxes[i].length;j++){
                    String a= checkBoxes[i][j].getText().toString();
                    if(!checkBoxes[i][j].equals(compoundButton)){
                        if(self.equals(a)){
                            checkBoxes[i][j].setEnabled(false);
                        }
                    }
                }
            }

        }
        else {
            for(int i =0; i<checkBoxes.length;i++){
                for(int j = 0; j<checkBoxes[i].length;j++){
                    String a= checkBoxes[i][j].getText().toString();
                    if(self.equals(a)){
                        checkBoxes[i][j].setEnabled(true);
                    }
                    if(checkBoxes[i][j].isChecked()){
                    }
                }
            }
        }

        check_three(checkBoxes);
    }
}