package com.example.library_app;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GeneralBorrowBookActivity extends AppCompatActivity {
    private ListView general_borrow_listview;
    private General_Borrow_ListViewAdapter adapter;

    private SwipeRefreshLayout general_borrow_swipe_refresh;


    private TextView expected_return_date;

    private void setReturnDate(TextView tv) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONDAY, 1);

        tv.setText(mFormat.format(cal.getTime()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_borrow);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        general_borrow_swipe_refresh = findViewById(R.id.general_borrow_swipe_refresh);
        general_borrow_listview = findViewById(R.id.general_borrow_listview);

        adapter = new General_Borrow_ListViewAdapter(GeneralBorrowBookActivity.this);
        general_borrow_listview.setAdapter(adapter);

        expected_return_date = findViewById(R.id.expected_return_date);
        setReturnDate(expected_return_date);

        adapter.borrow_addItem("헨젤과 그레텔", "A");
        adapter.borrow_addItem("햇님 달님", "B");
        adapter.borrow_addItem("흥부전", "C");
        adapter.borrow_addItem("신데렐라", "A");
        adapter.borrow_addItem("선녀와 나무꾼", "C");
        adapter.borrow_addItem("금도끼 은도끼", "B");
        adapter.borrow_addItem("빨간머리 앤", "A");
        adapter.borrow_addItem("헨젤과 그레텔", "A");
        adapter.borrow_addItem("햇님 달님", "B");
        adapter.borrow_addItem("흥부전", "C");
        adapter.borrow_addItem("신데렐라", "A");
        adapter.borrow_addItem("선녀와 나무꾼", "C");
        adapter.borrow_addItem("금도끼 은도끼", "B");
        adapter.borrow_addItem("빨간머리 앤", "A");
        adapter.borrow_addItem("헨젤과 그레텔", "A");
        adapter.borrow_addItem("햇님 달님", "B");
        adapter.borrow_addItem("흥부전", "C");
        adapter.borrow_addItem("신데렐라", "A");
        adapter.borrow_addItem("선녀와 나무꾼", "C");
        adapter.borrow_addItem("금도끼 은도끼", "B");
        adapter.borrow_addItem("빨간머리 앤", "A");

        general_borrow_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "TEST_TEST_TEST", Toast.LENGTH_SHORT).show();
                /*
                서버:
                해당 사용자가 대출 가능한 책이 추가되었을경우, 리스트뷰에 추가, 반납되었다면, 삭제
                 */
                //서버통신완료후 setRefreshing(false);
                general_borrow_swipe_refresh.setRefreshing(false);
            }
        });

    }

}
