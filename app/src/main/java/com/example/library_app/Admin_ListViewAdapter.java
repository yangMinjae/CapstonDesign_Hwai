package com.example.library_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Admin_ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Admin_ListItem> AdminListItems = new ArrayList<Admin_ListItem>();
    public Admin_ListViewAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return AdminListItems.size();
    }

    @Override
    public Object getItem(int i) {
        return AdminListItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // item_of_listview.xml 레이아웃을 inflate해서 참조획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.admin_item_of_listview, parent, false);
        }

        // item_of_listview.xml 의 참조 획득
        TextView txt_title = (TextView)convertView.findViewById(R.id.item_title);
        TextView txt_duedate = (TextView)convertView.findViewById(R.id.admin_item_duedate);
        TextView txt_current_location = (TextView)convertView.findViewById(R.id.admin_current_location);
        TextView txt_right_location = (TextView)convertView.findViewById(R.id.admin_right_location);

        Admin_ListItem adminListItem = AdminListItems.get(position);

        // 가져온 데이터를 텍스트뷰에 입력
        txt_title.setText(adminListItem.getTitle());
        txt_duedate.setText(adminListItem.getDuedate());
        txt_current_location.setText(adminListItem.getCurrent_location());
        txt_right_location.setText(adminListItem.getRight_location());

        return convertView;

    }

    public void additem(String title, String dudate, String current_location, String right_location){
        Admin_ListItem adminListItem = new Admin_ListItem();

        adminListItem.setTitle(title);
        adminListItem.setDuedate(dudate);
        adminListItem.setCurrent_location(current_location);
        adminListItem.setRight_location(right_location);

        AdminListItems.add(adminListItem);
    }
}
