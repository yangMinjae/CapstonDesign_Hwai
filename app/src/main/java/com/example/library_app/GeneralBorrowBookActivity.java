package com.example.library_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class GeneralBorrowBookActivity extends AppCompatActivity {
    private ListView general_borrow_listview;
    private General_Borrow_ListViewAdapter adapter;

    private SwipeRefreshLayout general_borrow_swipe_refresh;


    private TextView expected_return_date;

    private final String receiveUrl="books/list";
    private final int receiveRet=200;

    private final String lendUrl="books/lend";
    private final int lendRet=204;

    private int userId;

    public int getLendRet() {
        return lendRet;
    }

    public int getUserId() {
        return userId;
    }

    private void setReturnDate(TextView tv) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.DAY_OF_MONTH, 14);

        tv.setText(mFormat.format(cal.getTime()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_borrow);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent=getIntent();
        userId=intent.getIntExtra("id",-1);
        general_borrow_swipe_refresh = findViewById(R.id.general_borrow_swipe_refresh);
        general_borrow_listview = findViewById(R.id.general_borrow_listview);

        showView();

        expected_return_date = findViewById(R.id.expected_return_date);
        setReturnDate(expected_return_date);

        general_borrow_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "현재 회원님의 대출정보입니다.", Toast.LENGTH_SHORT).show();
                showView();
                general_borrow_swipe_refresh.setRefreshing(false);
            }
        });

    }
    public void showView(){
        adapter = new General_Borrow_ListViewAdapter(GeneralBorrowBookActivity.this);
        general_borrow_listview.setAdapter(adapter);

        adapter.borrow_reset();
        try{
            ServerTask_get task = new ServerTask_get(receiveUrl, receiveRet);
            task.execute();
            String rtnd_res= task.get();
            int rtndStatus = task.rtndStatus;

            if(rtndStatus==receiveRet){
                ArrayList<BookInfo> a = receiveParsing(rtnd_res);
                showBooklist(a);
            }else {
                JSONObject jsonObject = new JSONObject(rtnd_res);
                String errormessage = jsonObject.getString("message");
                Toast.makeText(getApplicationContext(),errormessage,Toast.LENGTH_LONG);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private ArrayList<BookInfo> receiveParsing(String json){       // 로그인 성공시 서버로 부터 받아온 정보를 LoginParse클래스에 저장 후 리턴
        try{
            JSONArray bookArray = new JSONArray(json);
            ArrayList<BookInfo> booklist = new ArrayList<BookInfo>();
            for(int i=0; i<bookArray.length();i++){
                JSONObject bookObject = bookArray.getJSONObject(i);
                BookInfo bookInfo = new BookInfo();
                bookInfo.setId(bookObject.getInt("id"));
                bookInfo.setTitle(bookObject.getString("title"));
                bookInfo.setCurrent(bookObject.getString("current"));
                booklist.add(bookInfo);

            }
            return  booklist;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    private void showBooklist(ArrayList<BookInfo> a){
        for (int i = 0; i < a.size(); i++) {
            BookInfo book=a.get(i);
            if(book.getCurrent().equals("null")){
                adapter.borrow_addItem(book.getTitle(),"?",book.getId());
            } else{
                adapter.borrow_addItem(book.getTitle(),book.getCurrent(),book.getId());
            }
        }
    }

    public int lendBook(int bookId, int userId){
        try{
            ServerTask_patch task = new ServerTask_patch(this.lendUrl, this.lendRet);
            String json = JsonString(bookId,userId);

            task.execute(json);
            String rtnd_res= task.get();
            int rtndStatus = task.rtndStatus;
            return rtndStatus;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    private String JsonString(int bookId, int userId) throws Exception{    // email과 pw를 jsonstring으로 변환하기위한 함수
        // 변환후, ServerTask_post.execute()의 인자로 사용됨
        HashMap<String, Integer> param = new HashMap<String, Integer>();
        param.put("bookId",bookId);
        param.put("userId",userId);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(param);
        return json;
    }

}
