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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminLoginActivity extends AppCompatActivity {

    private NavigationView navigationView;

    private SwipeRefreshLayout swipe_refresh;
    private ListView admin_listView1;
    private Admin_ListViewAdapter adapter;

    private String username;
    private int id;
    private int pinNum;
    
    private final String checklistUrl="books/checklist";
    private final int checklistRet=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent receive_intent = getIntent();
        username = receive_intent.getStringExtra("name");   //이전 인텐트(메인 로그인 화면)로 부터, 서버로 부터 받아온 name,id값 받아오기
        id = receive_intent.getIntExtra("id",-1);
        pinNum = receive_intent.getIntExtra("pinNum", -1);

        Log.d("test", ""+id+"/"+username+"/"+pinNum);

        swipe_refresh = findViewById(R.id.admin_swipe_refresh);
        admin_listView1 = findViewById(R.id.admin_listview_1);
        adapter = new Admin_ListViewAdapter(AdminLoginActivity.this);
        admin_listView1.setAdapter(adapter);
        getBooks();
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "현재 올바르지 않은 위치에 꽂힌 책들입니다.", Toast.LENGTH_SHORT).show();
                getBooks();
                swipe_refresh.setRefreshing(false);
            }
        });


        this.InitializeLayout();
        /*
        서버:
        서버에서 사용자 이름을 받아온다.
        username에 대입
         */
        navigationView=(NavigationView) findViewById(R.id.admin_nav_view);
        View headerview = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerview.findViewById(R.id.admin_text_header);
        navUsername.setText(username+"님 환영합니다.");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.admin_Home:
                        Toast.makeText(getApplicationContext(), "현재 Home 화면입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.admin_change:
                        showPinDialog();
                        break;
                    case R.id.admin_Modi_user:
                        Intent intent1=new Intent(AdminLoginActivity.this, ModifyPasswordActivity.class);
                        intent1.putExtra("admin",true);
                        intent1.putExtra("id",id);
                        startActivity(intent1);
                        break;
                    case R.id.logout:
                        finish();
                        break;
                }
                DrawerLayout drawer = findViewById(R.id.admin_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }
    public void InitializeLayout(){
        //toolBar를 통해 App Bar 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App Bar의 좌측 영영에 Drawer를 Open 하기 위한 Incon 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_menu_icon);
        getSupportActionBar().setTitle("관리자 로그인 홈");

        DrawerLayout drawLayout = findViewById(R.id.admin_drawer_layout);
        NavigationView navigationView = findViewById(R.id.admin_nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawLayout,
                toolbar,
                R.string.open,
                R.string.closed);

        drawLayout.addDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.admin_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void showPinDialog(){
        LayoutInflater vi=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout loginLayout=(LinearLayout) vi.inflate(R.layout.pin,null);

        final EditText pin= loginLayout.findViewById(R.id.Pin);

        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.setView(loginLayout).setPositiveButton("OK",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String str1=pin.getText().toString();
                if(pinNum==Integer.parseInt(str1)){
                    Intent intent=new Intent(AdminLoginActivity.this, AdminBookchangeActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(),"pin번호를 확인해 주세요",Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }
    public void getBooks(){
        adapter = new Admin_ListViewAdapter(AdminLoginActivity.this);
        admin_listView1.setAdapter(adapter);

        adapter.admin_reset();
        try{
            ServerTask_get task = new ServerTask_get(checklistUrl, checklistRet);
            task.execute();
            String rtnd_res= task.get();
            int rtndStatus = task.rtndStatus;
            Log.d("testCCC",rtnd_res);
            if(rtndStatus==checklistRet){
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
                bookInfo.setCurrent(bookObject.getString("current"));
                //bookInfo.setShelfid(bookObject.getString("shelf"));
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
                adapter.additem(book.getTitle(),book.getDue_date(),book.getCurrent(),"수정");
                //adapter.additem(book.getTitle(),book.getDue_date(),book.getCurrent(),book.getShelfid());
        }
    }
}