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

        Intent receive_intent = getIntent();        //?????? ?????????(?????? ????????? ??????)??? ??????, ????????? ?????? ????????? name,id??? ????????????
        username = receive_intent.getStringExtra("name");
        id = receive_intent.getIntExtra("id",-1);

        getBooks();
        general_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "?????? ???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                getBooks();
                general_swipe_refresh.setRefreshing(false);
            }
        });

        navigationView=(NavigationView) findViewById(R.id.general_nav_view);
        View headerview = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerview.findViewById(R.id.general_text_header);
        navUsername.setText(username+"??? ???????????????.");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.general_home:
                        Toast.makeText(getApplicationContext(), "?????? Home ???????????????.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.general_Modi_user:
                        Intent intent1=new Intent(GeneralLoginActivity.this, ModifyPasswordActivity.class);
                        intent1.putExtra("admin",false);
                        intent1.putExtra("id",id);
                        startActivity(intent1);
                        break;
                    case R.id.general_withdraw:
                        AlertDialog.Builder dlg = new AlertDialog.Builder(GeneralLoginActivity.this);
                        dlg.setTitle("????????????");
                        dlg.setMessage("????????? ?????????????????????????");
                        dlg.setIcon(R.drawable.warning);
                        dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
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
        //toolBar??? ?????? App Bar ??????
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App Bar??? ?????? ????????? Drawer??? Open ?????? ?????? Incon ??????
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_menu_icon);
        getSupportActionBar().setTitle("????????? ????????? ???");

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
    private ArrayList<BookInfo> getBooksParsing(String json){       // ????????? ????????? ????????? ?????? ????????? ????????? LoginParse???????????? ?????? ??? ??????
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
    private Boolean overDue(String due_date){   //??????????????? ????????? ???????????? ???????????? ??????????????? ??????. ???????????????(????????? ????????? ??????????????? ???????????????) true ??????
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
                Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.\n???????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

