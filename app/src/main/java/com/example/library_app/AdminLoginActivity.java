package com.example.library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class AdminLoginActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private final String pinnum="123456";

    //private SwipeRefreshLayout swipe_refresh;
    private ListView listView;
    private NestedScrollView scrollView;
    private ListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
        서버:
        서버에서 pinnum정보를 가져와서
        pinnum에 대입해준다.
         */

        //swipe_refresh = findViewById(R.id.swipe_refresh);
        scrollView = findViewById(R.id.admin_scrollview_1);
        listView = findViewById(R.id.admin_listview_1);
        adapter = new ListViewAdapter(AdminLoginActivity.this);
        listView.setAdapter(adapter);
        adapter.additem("잭과 콩나무1", "2022-02-03", false);
        adapter.additem("잭과 콩나무2", "2022-02-03", false);
        adapter.additem("잭과 콩나무3", "2022-02-03", false);
        adapter.additem("잭과 콩나무4", "2022-02-03", false);
        adapter.additem("잭과 콩나무5", "2022-02-03", false);
        adapter.additem("잭과 콩나무6", "2022-02-03", false);
        adapter.additem("잭과 콩나무7", "2022-02-03", false);
        adapter.additem("잭과 콩나무8", "2022-02-03", false);
        adapter.additem("잭과 콩나무9", "2022-02-03", false);
        adapter.additem("잭과 콩나무10", "2022-02-03", false);
        adapter.additem("잭과 콩나무11", "2022-02-03", false);
        adapter.additem("잭과 콩나무12", "2022-02-03", false);
        adapter.additem("잭과 콩나무13", "2022-02-03", false);
        adapter.additem("잭과 콩나무14", "2022-02-03", false);
        adapter.additem("잭과 콩나무15", "2022-02-03", false);
        adapter.additem("잭과 콩나무16", "2022-02-03", false);
        adapter.additem("잭과 콩나무17", "2022-02-03", false);
        adapter.additem("잭과 콩나무18", "2022-02-03", false);
        adapter.additem("잭과 콩나무19", "2022-02-03", false);
        adapter.additem("잭과 콩나무20", "2022-02-03", false);
        adapter.additem("잭과 콩나무21", "2022-02-03", false);
        adapter.additem("잭과 콩나무22", "2022-02-03", false);
//        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast.makeText(getApplicationContext(), "TEST_TEST_TEST", Toast.LENGTH_SHORT).show();
//                /*
//                서버:
//                해당 사용자가 빌린 책이 추가되었을경우, 리스트뷰에 추가, 반납되었다면, 삭제
//                 */
//            }
//        });


        /*
        서버:
        서버에서 해당 사용자가 빌린 모든 책의 정보를
        adapter.additem(~~~~)해준다.
         */

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        this.InitializeLayout();
        navigationView=findViewById(R.id.nav_view);
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
                        startActivity(intent1);
                        break;
                    case R.id.logout:
                        finish();
                        break;
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        DrawerLayout drawLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
                if(pinnum.equals(str1)){
                    Intent intent=new Intent(AdminLoginActivity.this, AdminBookchangeActivity.class);
                    startActivity(intent);
                }
            }
        }).show();
    }
}