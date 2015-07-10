package com.hungnt.customlogin;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListView_Activity extends Activity {

    private ArrayList<String> arrayStudent;
    private ListView listView;
    Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_view);
//
//        count = 0;
//        List<String> arrayString = Arrays.asList(getResources().getStringArray(R.array.danhsach));
//        listView = (ListView) findViewById(R.id.arrayStudent);

//        arrayStudent = new ArrayList<String>(arrayString);

        //Khoi tao doi tuong
        StudentAdapter studentAdapter = new StudentAdapter();
        listView.setAdapter(studentAdapter);
    }

    public class StudentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayStudent.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView tvName;

            if (convertView == null) {
                tvName = new TextView(ListView_Activity.this);
                count++;
                Log.i("List", "Khoi tao " + count);
            } else {
                tvName = (TextView) convertView;
            }

            tvName.setText(arrayStudent.get(position));
            tvName.setPadding(30, 10, 10, 30);
            return tvName;
        }
    }
}
