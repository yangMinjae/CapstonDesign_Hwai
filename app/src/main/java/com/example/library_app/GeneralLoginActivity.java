package com.example.library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GeneralLoginActivity extends AppCompatActivity {
    private NavigationView navigationView;

    private ListView general_listview_1;
    private SwipeRefreshLayout general_swipe_refresh;
    private General_ListViewAdapter adapter;
    private String username;
    private int id;

    private String getBookUrl="users/list/";
    private final int getBookRet=200;

    private String withDrawUrl="users/withdraw/";
    private final int withDrawRet=204;

    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_login_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.InitializeLayout();

        context=this.getApplicationContext();
        general_listview_1=findViewById(R.id.general_listview_1);
        general_swipe_refresh=findViewById(R.id.general_swipe_refresh);

        adapter = new General_ListViewAdapter(GeneralLoginActivity.this);
        general_listview_1.setAdapter(adapter);

        Intent receive_intent = getIntent();        //이전 인텐트(메인 로그인 화면)로 부터, 서버로 부터 받아온 name,id값 받아오기
        username = receive_intent.getStringExtra("name");
        id = receive_intent.getIntExtra("id",-1);

        getBooks();
        general_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "현재 대출중인 도서 목록입니다.", Toast.LENGTH_SHORT).show();
                getBooks();
                general_swipe_refresh.setRefreshing(false);
            }
        });

        navigationView=(NavigationView) findViewById(R.id.general_nav_view);
        View headerview = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerview.findViewById(R.id.general_text_header);
        navUsername.setText(username+"님 환영합니다.");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.general_home:
                        Toast.makeText(getApplicationContext(), "현재 Home 화면입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.general_Modi_user:
                        Intent intent1=new Intent(GeneralLoginActivity.this, ModifyPasswordActivity.class);
                        intent1.putExtra("admin",false);
                        intent1.putExtra("id",id);
                        startActivity(intent1);
                        break;
                    case R.id.general_withdraw:
                        AlertDialog.Builder dlg = new AlertDialog.Builder(GeneralLoginActivity.this);
                        dlg.setTitle("회원탈퇴");
                        dlg.setMessage("정말로 탈퇴하시겠습니까?");
                        dlg.setIcon(R.drawable.warning);
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                withDraw();
                            }
                        });
                        dlg.show();
                        break;
                    case R.id.logout:
                        finish();
                        break;
                    case R.id.lend_book:
                        Intent intent3 = new Intent(GeneralLoginActivity.this, GeneralBorrowBookActivity.class);
                        intent3.putExtra("id",id);
                        startActivity(intent3);
                        break;
                }
                DrawerLayout drawer = findViewById(R.id.general_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });





    }

    public void InitializeLayout() {
        //toolBar를 통해 App Bar 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App Bar의 좌측 영영에 Drawer를 Open 하기 위한 Incon 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_menu_icon);
        getSupportActionBar().setTitle("이용자 로그인 홈");

        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.general_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.general_nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawLayout,
                toolbar,
                R.string.open,
                R.string.closed);

        drawLayout.addDrawerListener(actionBarDrawerToggle);
    }

    public void getBooks(){
        adapter = new General_ListViewAdapter(GeneralLoginActivity.this);
        general_listview_1.setAdapter(adapter);

        adapter.general_reset();
        resetUrl_1();
        try{
            getBookUrl+=""+id;
            ServerTask_get task = new ServerTask_get(getBookUrl, getBookRet);
            task.execute();
            String rtnd_res= task.get();
            int rtndStatus = task.rtndStatus;
            Log.d("testBBB",rtnd_res);
            if(rtndStatus==getBookRet){
                ArrayList<BookInfo> a = getBooksParsing(rtnd_res);
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
    private ArrayList<BookInfo> getBooksParsing(String json){       // 로그인 성공시 서버로 부터 받아온 정보를 LoginParse클래스에 저장 후 리턴
        try{
            JSONArray bookArray = new JSONArray(json);
            ArrayList<BookInfo> booklist = new ArrayList<BookInfo>();
            for(int i=0; i<bookArray.length();i++){
                JSONObject bookObject = bookArray.getJSONObject(i);
                BookInfo bookInfo = new BookInfo();
                bookInfo.setTitle(bookObject.getString("title"));
                bookInfo.setDue_date(bookObject.getString("due_date"));
                bookInfo.setShelfid(bookObject.getString("shelf"));
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
            if(overDue(book.getDue_date())){
                adapter.additem(book.getTitle(),book.getDue_date(),true,book.getShelfid());
            }else{
                adapter.additem(book.getTitle(),book.getDue_date(),false,book.getShelfid());
            }

        }
    }
    private Boolean overDue(String due_date){   //반납기한이 오늘의 날짜보다 지났는지 확인해주는 함수. 연체됐으면(오늘의 날짜가 반납기한을 넘어갔으면) true 리턴
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date due = dateFormat.parse(due_date);

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            String current = dateFormat.format(date);
            Date today = dateFormat.parse(current);

            return due.before(today);

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void resetUrl_1(){
        getBookUrl="users/list/";
    }
    private void resetUrl_2(){
        withDrawUrl="users/withdraw/";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getBooks();
    }

    private void withDraw(){
        try{
            resetUrl_2();
            withDrawUrl+=id;
            ServerTask_delete task = new ServerTask_delete(withDrawUrl,withDrawRet);
            task.execute();
            String rtnd_res= task.get();
            int rtndStatus = task.rtndStatus;
            Log.d("withdraw",rtnd_res);

            if (rtndStatus==withDrawRet){
                Toast.makeText(getApplicationContext(), "회원탈퇴가 성공했습니다.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "회원탈퇴에 실패했습니다.\n대출중인 도서를 반납해주세요", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

