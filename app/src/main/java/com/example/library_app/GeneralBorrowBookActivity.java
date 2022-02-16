package com.example.library_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GeneralBorrowBookActivity extends AppCompatActivity {
    private ListView genearal_borrow_listview;
    private General_Borrow_LIstViewAdapter adapter;

    private TextView expected_return_date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_borrow);

        genearal_borrow_listview = (ListView) findViewById(R.id.general_borrow_listview);

        adapter = new General_Borrow_LIstViewAdapter(GeneralBorrowBookActivity.this);
        genearal_borrow_listview.setAdapter(adapter);

        expected_return_date = findViewById(R.id.expected_return_date);
        setReturnDate(expected_return_date);

    }

    private void setReturnDate(TextView tv) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONDAY, 1);

        tv.setText(mFormat.format(cal.getTime()));
    }
}
