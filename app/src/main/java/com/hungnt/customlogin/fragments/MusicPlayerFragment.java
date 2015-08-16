package com.hungnt.customlogin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungnt.customlogin.ListSongActivity;
import com.hungnt.customlogin.R;
import com.hungnt.customlogin.utils.CheckBoxImageView;

/**
 * Created by HungNT on 17/August/2015.
 */
public class MusicPlayerFragment extends Fragment implements View.OnClickListener, CheckBoxImageView.OnCheckedChangeListener {

    ListSongActivity mainActivity;
    private static final String TAG = "hung";
    private static final String DRAWABLE = "drawable://";
    private CheckBoxImageView btn_play, btn_shuffle, btn_repeat;
    private TextView tvName, tvArtist;
    private ImageView img_artwork;
    private int pos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_music_play, container, false);

        pos = getArguments().getInt("pos");

        mainActivity = (ListSongActivity) getActivity();

        btn_play = (CheckBoxImageView) v.findViewById(R.id.btn_play);
        btn_shuffle = (CheckBoxImageView) v.findViewById(R.id.btn_shuffle);
        btn_repeat = (CheckBoxImageView) v.findViewById(R.id.btn_repeat);

        tvName = (TextView) v.findViewById(R.id.tv_name_song);
        tvArtist = (TextView) v.findViewById(R.id.tv_artist);
        img_artwork = (ImageView) v.findViewById(R.id.img_artwork);

        tvName.setText(mainActivity.songs.get(pos).getName());
        tvArtist.setText(mainActivity.songs.get(pos).getAuthor());
//        img_artwork.setImageResource(mainActivity.songs.get(pos).getCover());
        loadImage();

        v.findViewById(R.id.btn_prev).setOnClickListener(this);
        v.findViewById(R.id.btn_next).setOnClickListener(this);
        v.findViewById(R.id.btn_back).setOnClickListener(this);
        v.findViewById(R.id.btn_playlist).setOnClickListener(this);


        btn_play.setOnCheckedChangeListener(this);
        btn_shuffle.setOnCheckedChangeListener(this);
        btn_repeat.setOnCheckedChangeListener(this);


        return v;
    }

    public void loadImage() {
        mainActivity.imageLoader.displayImage("drawable://" + mainActivity.songs.get(pos).getCover(), img_artwork);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                //TODO add code here
                Log.d(TAG, "onClick next");
                break;
            case R.id.btn_prev:
                //TODO add code here
                Log.d(TAG, "onClick prev");
                break;
            case R.id.btn_back:
                mainActivity.getSupportFragmentManager().popBackStack();
                break;
            case R.id.btn_playlist:
                //TODO add code here
                Log.d(TAG, "onClick playlist");
                break;
        }

    }

    @Override
    public void onCheckedChanged(View buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.btn_play:
                if (btn_play.isChecked()) {
                    //TODO play nhac
                    Log.d(TAG, "onCheckedChanged play");
                } else {
                    //TODO pause
                    Log.d(TAG, "onCheckedChanged pause");
                }
                break;
            case R.id.btn_repeat:
                if (!btn_repeat.isChecked()) {
                    //TODO off repeat
                    Log.d(TAG, "onCheckedChanged off");
                } else {
                    //TODO on repeat
                    Log.d(TAG, "onCheckedChanged on");
                }
                break;
            case R.id.btn_shuffle:
                if (!btn_shuffle.isChecked()) {
                    //TODO off
                    Log.d(TAG, "onCheckedChanged off");
                } else {
                    //TODO on
                    Log.d(TAG, "onCheckedChanged on");
                }
                break;
        }
    }
}
