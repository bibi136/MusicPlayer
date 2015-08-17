package com.hungnt.customlogin.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hungnt.customlogin.ListSongActivity;
import com.hungnt.customlogin.R;
import com.hungnt.customlogin.Services.MediaReciver;
import com.hungnt.customlogin.Services.MediaService;
import com.hungnt.customlogin.utils.CheckBoxImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerFragment extends Fragment implements View.OnClickListener, CheckBoxImageView.OnCheckedChangeListener {

    ListSongActivity mainActivity;
    private static final String TAG = "hung";
    private CheckBoxImageView btn_play, btn_shuffle, btn_repeat;
    private TextView tvName, tvArtist;
    private ImageView img_artwork;
    private int pos;
    private boolean isPlay;

    Intent intentGetCurrentTime;
    //	Intent intent = new Intent(this, MediaService.class);
    BroadcastReceiver receiver;
    public static final int START = 0, RESUME = 1, STOP = 2, GET_TIME = 3,
            GET_FINAL_TIME = 4, UPDATE_STASTUS = 5, SAVE_LIST = 6,
            SET_REPEAT = 7, SEEK = 8;
    public static final String COMMAND = "command", RESPONE = "respone",
            TIME_RESPONE = "time", PLAY = "isPlaying", PAUSSE = "isPause",
            REPEAT_STATUS = "repeat_stt", LIST_SONG = "list_song", SEEK_TO_TIME = "seekk",
            VALUES = "values";
    public static final int COMPLETE = 0, TIME = 1, FTIME = 2, UPDATE = 3;
    private boolean isUpdated = false;
    private int repeateStatus = 0;
    public static boolean isPlaying = false;
    boolean isFirstTimePressPlay = true;
    private double finalTime;
    private double currentTime;
    private Cursor cursor;
    private SeekBar timeSeekbar;
    private TextView tvCurrentTime, tvTotalTime;
    private int currentSong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_music_play, container, false);

        pos = getArguments().getInt("pos");
        isPlay = getArguments().getBoolean("play");

        mainActivity = (ListSongActivity) getActivity();
        Intent intent = new Intent(mainActivity, MediaService.class);
        intent.putExtra(COMMAND, UPDATE_STASTUS);
        mainActivity.startService(intent);

        btn_play = (CheckBoxImageView) v.findViewById(R.id.btn_play);
        btn_shuffle = (CheckBoxImageView) v.findViewById(R.id.btn_shuffle);
        btn_repeat = (CheckBoxImageView) v.findViewById(R.id.btn_repeat);

        tvName = (TextView) v.findViewById(R.id.tv_name_song);
        tvArtist = (TextView) v.findViewById(R.id.tv_artist);
        img_artwork = (ImageView) v.findViewById(R.id.img_artwork);
        tvTotalTime = (TextView) v.findViewById(R.id.tv_end_time);
        tvCurrentTime = (TextView) v.findViewById(R.id.tv_curr_time);

        tvName.setText(mainActivity.songs.get(pos).getName());
        tvArtist.setText(mainActivity.songs.get(pos).getAuthor());
        loadImage();

        v.findViewById(R.id.btn_prev).setOnClickListener(this);
        v.findViewById(R.id.btn_next).setOnClickListener(this);
        v.findViewById(R.id.btn_back).setOnClickListener(this);
        v.findViewById(R.id.btn_playlist).setOnClickListener(this);
        v.findViewById(R.id.music_layout).setOnClickListener(this);

        timeSeekbar = (SeekBar) v.findViewById(R.id.seek_bar);

        timeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (isPlaying)
                    Log.d(TAG, "onStopTrackingTouch ");
                    seekTime();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                if (isPlaying)
//                    Log.d(TAG, "onStartTrackingTouch ");
//                    seekTime();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (!isPlaying && isFirstTimePressPlay) {
//                    Log.d(TAG, "onProgressChanged ");
//                    seekTime();
//                }
            }
        });


        btn_play.setOnCheckedChangeListener(this);
        btn_shuffle.setOnCheckedChangeListener(this);
        btn_repeat.setOnCheckedChangeListener(this);

        btn_play.setChecked(true);


        receiver = new MediaReciver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int responeInfo = intent.getExtras().getInt(RESPONE);
                switch (responeInfo) {
                    case COMPLETE:
                        onComplete();
                        break;

                    case TIME:
                        String time = intent.getExtras().getString(TIME_RESPONE);
                        currentTime = Double.parseDouble(time);
                        tvCurrentTime
                                .setText(String
                                        .format("%02d:%02d",
                                                TimeUnit.MILLISECONDS
                                                        .toMinutes((long) currentTime),
                                                TimeUnit.MILLISECONDS
                                                        .toSeconds((long) currentTime)
                                                        - TimeUnit.MINUTES
                                                        .toSeconds(TimeUnit.MILLISECONDS
                                                                .toMinutes((long) currentTime))));
                        timeSeekbar.setProgress((int) currentTime);
                        break;
                    case FTIME:
                        getFinalTime(intent);
                        break;
                    case UPDATE:
                        getFinalTime(intent);

//					String time1 = intent.getExtras().getString(REPEAT_STATUS);
//					if(string)
                        repeateStatus = intent.getExtras().getInt(REPEAT_STATUS);
//					Toast.makeText(getApplicationContext(), ""+repeateStatus, Toast.LENGTH_SHORT).show();
                        if (repeateStatus == 1) {
                            btn_repeat.setChecked(true);
                        } else {
                            btn_repeat.setChecked(false);
                        }
                        ArrayList<String> listSongTmp = intent
                                .getStringArrayListExtra(LIST_SONG);
                        ArrayList<String> valuesTmp = intent
                                .getStringArrayListExtra(VALUES);
                        isPlaying = intent.getExtras().getBoolean(PLAY);
                        if (listSongTmp != null && valuesTmp != null) {
                            isUpdated = true;

                        }
                        if (isPlaying) {
                            btn_play.setChecked(true);
                            isFirstTimePressPlay = false;
                        }
                        boolean isPause = intent.getExtras().getBoolean(PAUSSE);
                        if (isPause) {
                            btn_play.setChecked(false);
                            isFirstTimePressPlay = false;
                        }
                        break;
                }

            }

        };
        return v;
    }



    @Override
    public void onStart() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RESPONE);
        mainActivity.registerReceiver(receiver, intentFilter);
        if (isPlay) {
            currentSong = pos;
            startMedia(mainActivity.songs.get(currentSong).getPath());
        }
        super.onStart();
    }

    public void loadImage() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_artwork)
                .showImageForEmptyUri(R.drawable.img_artwork)
                .showImageOnFail(R.drawable.img_artwork)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        String url = mainActivity.songs.get(currentSong).getCover();
        mainActivity.imageLoader.displayImage("file://" + url, img_artwork, options);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                currentSong++;
                if (currentSong >= mainActivity.songs.size()) {
                    currentSong = mainActivity.songs.size() - 1;
                }
                isFirstTimePressPlay = true;
                startMedia(mainActivity.songs.get(currentSong).getPath());
                tvName.setText(mainActivity.songs.get(currentSong).getName());
                tvArtist.setText(mainActivity.songs.get(currentSong).getAuthor());
                loadImage();
                break;
            case R.id.btn_prev:
                currentSong--;
                if (currentSong < 0) {
                    currentSong = 0;
                }
                isFirstTimePressPlay = true;
                startMedia(mainActivity.songs.get(currentSong).getPath());
                tvName.setText(mainActivity.songs.get(currentSong).getName());
                tvArtist.setText(mainActivity.songs.get(currentSong).getAuthor());
                loadImage();
                Log.d(TAG, "onClick prev");
                break;
            case R.id.btn_back:
                mainActivity.setPos(currentSong);
                mainActivity.tv_name_song_playing.setText(mainActivity.songs.get(currentSong).getName());
                mainActivity.tv_artist_playing_song.setText(mainActivity.songs.get(currentSong).getAuthor());
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
                    startMedia(mainActivity.songs.get(currentSong).getPath());
                    Log.d(TAG, "onCheckedChanged play");
                } else {
                    stopMedia();
                    Log.d(TAG, "onCheckedChanged pause");
                }
                break;
            case R.id.btn_repeat:
                if (!btn_repeat.isChecked()) {
                    //off repeat
                    Log.d(TAG, "onCheckedChanged off");
                    Intent intent11 = new Intent(mainActivity, MediaService.class);
                    intent11.putExtra(COMMAND, SET_REPEAT);
                    intent11.putExtra(REPEAT_STATUS, 0);
                    mainActivity.startService(intent11);
                    Toast.makeText(getActivity(), "No Repeat",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // on repeat
                    Intent intent = new Intent(mainActivity, MediaService.class);
                    intent.putExtra(COMMAND, SET_REPEAT);
                    intent.putExtra(REPEAT_STATUS, 1);
                    mainActivity.startService(intent);
                    Toast.makeText(getActivity(), "Repeat one song",
                            Toast.LENGTH_SHORT).show();
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

    private void startMedia(String url) {
        Intent intent = new Intent(mainActivity, MediaService.class);
        if (isFirstTimePressPlay) {
            intent.putExtra(COMMAND, START);
            intent.putExtra("url", url);
            intent.putExtra("finaltime", GET_FINAL_TIME);
            isFirstTimePressPlay = false;
        } else {
            intent.putExtra(COMMAND, RESUME);
        }
        mainActivity.startService(intent);
        isPlaying = true;
//        btnPlayStop.setText("Pause");
        btn_play.setChecked(true);
    }

    private void stopMedia() {
        Intent intent = new Intent(mainActivity, MediaService.class);
        intent.putExtra(COMMAND, STOP);
        mainActivity.startService(intent);
        isPlaying = false;
//        btnPlayStop.setText("Play");
        btn_play.setChecked(false);

    }


    private void getFinalTime(Intent intent) {
        String time1 = intent.getExtras().getString(TIME_RESPONE);
        finalTime = Double.parseDouble(time1);
        tvTotalTime.setText(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                        .toMinutes((long) finalTime))));
        timeSeekbar.setMax((int) finalTime);
    }

    public void onComplete() {
        isPlaying = false;
        isFirstTimePressPlay = true;
        if (repeateStatus == 1) {// neu k looping
            btn_play.setChecked(true);
        } else {
            btn_play.setChecked(false);
        }
        timeSeekbar.setProgress(0);
        tvCurrentTime.setText("00:00");
    }

    @Override
    public void onPause() {
        mainActivity.unregisterReceiver(receiver);
        super.onPause();
    }

    public void seekTime() {
        Log.d(TAG, "seekTime ");
        Intent intent = new Intent(mainActivity, MediaService.class);
        intent.putExtra(COMMAND, SEEK);
        intent.putExtra(SEEK_TO_TIME, timeSeekbar.getProgress());
        mainActivity.startService(intent);
    }
}
