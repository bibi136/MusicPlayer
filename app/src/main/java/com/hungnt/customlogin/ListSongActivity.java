package com.hungnt.customlogin;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class ListSongActivity extends Activity {

    private Context context;
    ListView lvSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lvSong = (ListView) findViewById(R.id.lvSong);

        ListSong listSong = new ListSong();
        Log.i("oncreate","Tao listsong");

        lvSong.setAdapter(listSong);

        setContentView(R.layout.activity_list_song);
    }

    class ListSong extends BaseAdapter {


        @Override
        public int getCount() {
            return 15;
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
                Log.i("hung", "tao view");
//                LayoutInflater inflater = (LayoutInflater)
//                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.layout_row, parent, false);
                view = getLayoutInflater().inflate(R.layout.layout_row,parent,false);
            }

            return view;
        }
    }
}
