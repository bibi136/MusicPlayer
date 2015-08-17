package com.hungnt.customlogin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hungnt.customlogin.ListSongActivity;
import com.hungnt.customlogin.Objs.SongInfo;

import java.util.ArrayList;

/**
 * Created by Hoang on 8/12/2015.
 */
public class SongsAdapter extends ArrayAdapter<SongInfo> {
    private ListSongActivity mainActivity;
    private int resource;
    private ArrayList<SongInfo> listSongs;

    public SongsAdapter(ListSongActivity context, int resource, ArrayList<SongInfo> objects) {
        super(context, resource, objects);
        this.mainActivity = context;
        this.resource = resource;
        this.listSongs = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = mainActivity.getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }


        return convertView;
    }
}
