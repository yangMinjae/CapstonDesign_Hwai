package com.example.library_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class General_ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<General_ListItem> generalListItems = new ArrayList<General_ListItem>();
    public General_ListViewAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return generalListItems.size();
    }

    @Override
    public Object getItem(int i) {
        return generalListItems.get(i);
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
            convertView = inflater.inflate(R.layout.general_item_of_listview, parent, false);
        }

        // item_of_listview.xml 의 참조 획득
        TextView txt_title = (TextView)convertView.findViewById(R.id.item_title);
        TextView txt_duedate = (TextView)convertView.findViewById(R.id.general_item_duedate);
        TextView txt_right_location = (TextView)convertView.findViewById(R.id.general_right_location);

        General_ListItem generalListItem = generalListItems.get(position);

        // 가져온 데이터를 텍스트뷰에 입력
        txt_title.setText(generalListItem.getTitle());
        // 연체된 책일 경우 책의 제목이 쓰이는 TextView 색이 빨간색, 아닐경우 어두운 코발트색
        txt_title.setTextColor((generalListItem.getOverdue())? ContextCompat.getColor(mContext, R.color.over):
                ContextCompat.getColor(mContext, R.color.not_over));

        // 제출기한 텍스트뷰에 입력
       txt_duedate.setText(generalListItem.getDuedate());
       txt_right_location.setText(generalListItem.getRight_location());


        return convertView;

    }

    public void additem(String title, String dudate, Boolean overdue , String right_location){
        General_ListItem generalListItem = new General_ListItem();

        generalListItem.setTitle(title);
        generalListItem.setDuedate(dudate);
        generalListItem.setOverdue(overdue);
        generalListItem.setRight_location(right_location);

        generalListItems.add(generalListItem);
    }
    public void general_reset(){
        generalListItems.clear();
    }
}
