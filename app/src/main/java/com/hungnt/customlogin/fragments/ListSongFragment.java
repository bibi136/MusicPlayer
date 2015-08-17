package com.hungnt.customlogin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hungnt.customlogin.ListSongActivity;
import com.hungnt.customlogin.R;
import com.hungnt.customlogin.adapters.SongsAdapter;


/**
 * Created by HungNT on 17/August/2015.
 */
public class ListSongFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    SongsAdapter songsAdapter;
    ListSongActivity listSongActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listSongActivity = (ListSongActivity) getActivity();
        View v = inflater.inflate(R.layout.frag_list_songs, container, false);
        listView = (ListView) v.findViewById(R.id.lvSong);

        songsAdapter = new SongsAdapter(listSongActivity, R.layout.item_listview_song, listSongActivity.songs);
        listView.setAdapter(songsAdapter);

        listView.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO bat su kien play nhac khi chon 1 bai hat
        Log.d("hung", "onItemClick ");

        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);

        MusicPlayerFragment playerFragment = new MusicPlayerFragment();
        playerFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = listSongActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.mainLayout, playerFragment).addToBackStack(null).commit();

    }
}
