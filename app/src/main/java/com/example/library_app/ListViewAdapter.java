package com.example.library_app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ListItem> listItems= new ArrayList<ListItem>();
    public ListViewAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
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
            convertView = inflater.inflate(R.layout.item_of_listview, parent, false);
        }

        // item_of_listview.xml 의 참조 획득
        TextView txt_title = (TextView)convertView.findViewById(R.id.item_title);
        TextView txt_duedate = (TextView)convertView.findViewById(R.id.item_duedate);

        ListItem listItem = listItems.get(position);

        // 가져온 데이터를 텍스트뷰에 입력
        txt_title.setText(listItem.getTitle());
        // 연체된 책일 경우 책의 제목이 쓰이는 TextView 색이 빨간색, 아닐경우 어두운 코발트색
        txt_title.setBackgroundColor((listItem.getOverdue())? ContextCompat.getColor(mContext, R.color.over):
                ContextCompat.getColor(mContext, R.color.not_over));

        // 제출기한 텍스트뷰에 입력
       txt_duedate.setText(listItem.getDuedate());


        return convertView;

    }

    public void additem(String title, String dudate, Boolean overdue){
        ListItem listItem = new ListItem();

        listItem.setTitle(title);
        listItem.setDuedate(dudate);
        listItem.setOverdue(overdue);

        listItems.add(listItem);
    }
}
