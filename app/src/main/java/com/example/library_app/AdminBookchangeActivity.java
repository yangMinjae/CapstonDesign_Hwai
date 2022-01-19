package com.example.library_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminBookchangeActivity extends AppCompatActivity {

    private Button btn_chg1;    // 1층의 카테고리 변경 버튼
    private Button btn_chg2;    // 2층의 카테고리 변경 버튼
    private Button btn_chg3;    // 3층의 카테고리 변경 버튼
    private TextView category1; // 1층의 카테고리
    private TextView category2; // 2층의 카테고리
    private TextView category3; // 3층의 카테고리
    
    // 카테고리 변경 버튼 클릭 이벤트 리스너
    class chgBtnListener implements View.OnClickListener {
        // 카테고리 배열 string.xml에서 추가 및 삭제
        final String[] categories=getResources().getStringArray(R.array.book_category);
        @Override
        public void onClick(View view) {
            // 대화 상자 생성
            AlertDialog.Builder dlg = new AlertDialog.Builder(AdminBookchangeActivity.this);

            dlg.setTitle("카테고리를 선택하세요.");
            dlg.setItems(categories, new DialogInterface.OnClickListener() {
                @Override
                // 선택된 버튼의 층에 맞는 카테고리 변경
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (view.getId()) {
                        case R.id.btn_chg1:
                            category1.setText(categories[i]);
                            break;
                        case R.id.btn_chg2:
                            category2.setText(categories[i]);
                            break;
                        case R.id.btn_chg3:
                            category3.setText(categories[i]);
                            break;
                    }
                }
            });

            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int i) {
                    Toast.makeText(AdminBookchangeActivity.this, "카테고리가 변경되었습니다.", Toast.LENGTH_LONG).show();
                }
            });
            dlg.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_bookchange);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btn_chg1 = findViewById(R.id.btn_chg1);
        btn_chg2 = findViewById(R.id.btn_chg2);
        btn_chg3 = findViewById(R.id.btn_chg3);
        category1 = findViewById(R.id.category1);
        category2 = findViewById(R.id.category2);
        category3 = findViewById(R.id.category3);

        chgBtnListener btnListener = new chgBtnListener();
        btn_chg1.setOnClickListener(btnListener);
        btn_chg2.setOnClickListener(btnListener);
        btn_chg3.setOnClickListener(btnListener);

    }
}