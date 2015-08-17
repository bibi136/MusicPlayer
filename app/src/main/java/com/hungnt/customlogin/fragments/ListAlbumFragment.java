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
import com.hungnt.customlogin.adapters.AlbumAdapter;


/**
 * Created by HungNT on 17/August/2015.
 */
public class ListAlbumFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    AlbumAdapter adapter;
    ListSongActivity listSongActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listSongActivity = (ListSongActivity) getActivity();
        View v = inflater.inflate(R.layout.frag_list_songs, container, false);
        listView = (ListView) v.findViewById(R.id.lvSong);

        adapter = new AlbumAdapter(listSongActivity, R.layout.item_list_album, listSongActivity.albums);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO bat su kien play nhac khi chon 1 bai hat
        Log.d("hung", "onItemClick ");

        listSongActivity.layoutPlaying.setVisibility(View.VISIBLE);
        listSongActivity.tv_name_song_playing.setText(listSongActivity.songs.get(position).getName());
        listSongActivity.tv_artist_playing_song.setText(listSongActivity.songs.get(position).getAuthor());

        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);

        MusicPlayerFragment playerFragment = new MusicPlayerFragment();
        playerFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = listSongActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.mainLayout, playerFragment).addToBackStack(null).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
