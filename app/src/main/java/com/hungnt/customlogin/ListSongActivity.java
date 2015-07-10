package com.hungnt.customlogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class ListSongActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView lvSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

        lvSong = (ListView) findViewById(R.id.lvSong);
        ListSong listSong = new ListSong();
        lvSong.setAdapter(listSong);

        lvSong.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class ListSong extends BaseAdapter {


        @Override
        public int getCount() {
            return 20;
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

            View view = convertView;

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.layout_row, parent, false);
            } else {
                view = convertView;
            }
            return view;
        }
    }
}
