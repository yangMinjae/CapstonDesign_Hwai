package com.example.library_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class AdminBookchangeActivity extends AppCompatActivity {

    final String[] categories=getResources().getStringArray(R.array.book_category); // 카테고리 배열 string.xml에서 추가 및 삭제

    private Button btn_chg;     // 카테고리 변경 버튼
    private TextView category1; // 1층의 카테고리
    private TextView category2; // 2층의 카테고리
    private TextView category3; // 3층의 카테고리

    private CheckBox[][] checkBoxes;

    private String updated_category1;
    private String updated_category2;
    private String updated_category3;

    // 카테고리 변경 버튼 클릭 이벤트 리스너
    class chgBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (btn_chg.getText() == "변경") {
                btn_chg.setText("확인");
                for (int i = 0; i < checkBoxes.length; i++) {
                    for (int j = 0; j < checkBoxes[0].length; j++) {
                        checkBoxes[i][j].setVisibility(View.VISIBLE);
                    }
                }
            }
            else {
                btn_chg.setText("변경");
                for (int i = 0; i < checkBoxes.length; i++) {
                    for (int j = 0; j < checkBoxes[0].length; j++) {
                        checkBoxes[i][j].setVisibility(View.INVISIBLE);
                    }
                }

                check_category(checkBoxes[0], category1);
                check_category(checkBoxes[1], category2);
                check_category(checkBoxes[2], category3);

            }

        }
    }

    private void check_category(CheckBox[] cb, TextView tv) {
        tv.setText("");
        for (int i = 0; i < categories.length; i++) {
            tv.append(categories[i] + " ");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_bookchange);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btn_chg = findViewById(R.id.btn_chg);
        category1 = findViewById(R.id.category1);
        category2 = findViewById(R.id.category2);
        category3 = findViewById(R.id.category3);

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
        chgBtnListener btnListener = new chgBtnListener();
        btn_chg.setOnClickListener(btnListener);
    }
}