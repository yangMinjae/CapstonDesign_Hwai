package com.example.library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class GeneralLoginActivity extends AppCompatActivity {
    private NavigationView navigationView;

    private ListView general_listview_1;
    private SwipeRefreshLayout general_swipe_refresh;
    private General_ListViewAdapter adapter;
    private String username;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_login_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.InitializeLayout();

        general_listview_1=findViewById(R.id.general_listview_1);
        general_swipe_refresh=findViewById(R.id.general_swipe_refresh);

        adapter = new General_ListViewAdapter(GeneralLoginActivity.this);
        general_listview_1.setAdapter(adapter);

        Intent receive_intent = getIntent();        //이전 인텐트(메인 로그인 화면)로 부터, 서버로 부터 받아온 name,id값 받아오기
        username = receive_intent.getStringExtra("name");
        id = receive_intent.getIntExtra("id",-1);

                /*
        서버:
        서버에서 해당 사용자가 빌린 모든 책의 정보를
        adapter.additem(~~~~)해준다.
         */
        adapter.additem("잭과 콩나무1", "2022-02-03", false ,"R층");    //서버 작업 완료후 삭제
        adapter.additem("잭과 콩나무2", "2022-02-03", true,"R층");
        adapter.additem("잭과 콩나무3", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무4", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무5", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무6", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무7", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무8", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무9", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무10", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무11", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무12", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무13", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무14", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무15", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무16", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무17", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무18", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무19", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무20", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무21", "2022-02-03", false,"R층");
        adapter.additem("잭과 콩나무22", "2022-02-03", false,"R층");
        general_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "TEST_TEST_TEST", Toast.LENGTH_SHORT).show();
                /*
                서버:
                해당 사용자가 빌린 책이 추가되었을경우, 리스트뷰에 추가, 반납되었다면, 삭제
                 */
                //서버통신완료후 setRefreshing(false);
                general_swipe_refresh.setRefreshing(false);
            }
        });
        /*
        서버:
        서버에서 사용자 이름을 받아온다.
        username에 대입
         */
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
                    case R.id.logout:
                        finish();
                        break;
                    case R.id.lend_book:
                        Intent intent2 = new Intent(GeneralLoginActivity.this, GeneralBorrowBookActivity.class);
                        intent2.putExtra("id",id);
                        startActivity(intent2);
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

}

