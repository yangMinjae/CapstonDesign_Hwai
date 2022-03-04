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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

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

    private final String shelfUrl="categories/shelf";
    private final int shelfRet=200;

    private final String changeUrl="categories/change";
    private final int changeRet=204;

    private String rtnd_res;

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

    private void get_category() {
        try{
            ServerTask_get task = new ServerTask_get(shelfUrl, shelfRet);
            task.execute();
            rtnd_res = task.get();
            Log.d("testAAAA", rtnd_res);
            if(task.rtndStatus == shelfRet){
                try{
                    JSONArray jsonArray = new JSONArray(rtnd_res);
                    String[] category_ = new String[jsonArray.length()];
                    for (int i=0; i<category_.length; i++) {
                        category_[i] = "";
                    }
                    for (int i=0; i<category_.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String genre = jsonObject.getString("genre");
                        int shelf = Integer.parseInt(jsonObject.getString("shelf"));
                        if (shelf != -1){
                            category_[shelf-1] += genre + ", ";
                        }
                    }
                    updated_category1 = category_[0].substring(0, category_[0].length()-2);
                    updated_category2 = category_[1].substring(0, category_[1].length()-2);
                    updated_category3 = category_[2].substring(0, category_[2].length()-2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String location_genre(String genre) {
        String[] category1_array = category1.getText().toString().split(", ");
        String[] category2_array = category2.getText().toString().split(", ");
        String[] category3_array = category3.getText().toString().split(", ");

        if (Arrays.asList(category1_array).contains(genre)) {
            return "1";
        }
        else if (Arrays.asList(category2_array).contains(genre)) {
            return "2";
        }
        else if (Arrays.asList(category3_array).contains(genre)) {
            return "3";
        }
        else { return "-1"; }
    }

    private void send_category() {
        try{
            ServerTask_patch task = new ServerTask_patch(changeUrl, changeRet);
            String data = "[";
            for (int i = 0; i < categories.length; i++) {
                String location = location_genre(categories[i]);
                String data_ = "{\"genre\":\"" + categories[i] + "\",\"shelf\":\"" + location + "\"},";
                data += data_;
            }
            data = data.substring(0,data.length()-1);
            data += "]";

            task.execute(data);
            rtnd_res = task.get();
            if (task.rtndStatus == changeRet) {
                Log.d("TAG", "성공");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        get_category();
        category1.setText(updated_category1);
        category2.setText(updated_category2);
        category3.setText(updated_category3);


        View.OnClickListener chg_btn_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_chg.getText().toString().equals("변경")) {
                    btn_chg.setText("확인");
                    btn_submitCategory.setEnabled(false);
                    cb_list_1f.setVisibility(View.VISIBLE);
                    cb_list_2f.setVisibility(View.VISIBLE);
                    cb_list_3f.setVisibility(View.VISIBLE);

                }
                else {
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
                        btn_chg.setEnabled(false);
                        btn_submitCategory.setEnabled(true);
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
                send_category();
                btn_chg.setText("변경");
                btn_chg.setEnabled(true);
                btn_submitCategory.setEnabled(false);
                Toast.makeText(getApplicationContext(), "변경이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                get_category();
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
    }
}