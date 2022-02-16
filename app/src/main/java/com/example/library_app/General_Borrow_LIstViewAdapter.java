package com.example.library_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class General_Borrow_LIstViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<General_Borrow_ListItem> borrow_listItems = new ArrayList<General_Borrow_ListItem>();

    public General_Borrow_LIstViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return borrow_listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return borrow_listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.general_borrow_item_of_listview, parent, false);
        }

        TextView book_title = (TextView) convertView.findViewById(R.id.book_title);
        TextView book_location = (TextView) convertView.findViewById(R.id.book_location);
        Button btn_borrow = (Button) convertView.findViewById(R.id.btn_borrow);

        General_Borrow_ListItem general_borrow_listItem = borrow_listItems.get(position);

        book_title.setText(general_borrow_listItem.getTitle());
        book_location.setText("위치 : ");
        book_location.append(general_borrow_listItem.getLocation());

        btn_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                대출 버튼을 누른 책을 리스트에서 없애고,
                서버로 대출 정보 전송
                 */
                Toast.makeText(mContext.getApplicationContext(), "대출 버튼 눌림", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void addItem(String title, String location) {
        General_Borrow_ListItem listItem = new General_Borrow_ListItem();

        listItem.setTitle(title);
        listItem.setLocation(location);

        borrow_listItems.add(listItem);
    }
}
