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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hungnt.customlogin.R.array.danhsach;


public class ListSongActivity extends Activity {

    ListView lvSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

        lvSong = (ListView) findViewById(R.id.lvSong);
        ListSong listSong = new ListSong();
        Log.i("oncreate", "Tao listsong");

        lvSong.setAdapter(listSong);

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
                Log.i("hung", "tao view");
                view = getLayoutInflater().inflate(R.layout.layout_row, parent, false);
            } else {
                view = convertView;
            }
            return view;
        }
    }
}
