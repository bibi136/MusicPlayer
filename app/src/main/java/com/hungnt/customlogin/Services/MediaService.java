package com.hungnt.customlogin.Services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.hungnt.customlogin.fragments.MusicPlayerFragment;

import java.io.IOException;
import java.util.ArrayList;

public class MediaService extends Service implements OnPreparedListener,
        OnCompletionListener, OnSeekCompleteListener, OnErrorListener,
        OnInfoListener, OnBufferingUpdateListener {
    MediaPlayer mediaPlayer;
    private int repeatStatus = 0;
    private int count;
    private String sntAudioLink;
    private boolean isPrepare = false;
    Cursor cursor;
    ArrayList<String> listSong;
    ArrayList<String> values;
    Handler mHandler = new Handler();
    private boolean isPause = false;

    @Override
    public void onCreate() {
        count = 0;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // set listeners
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int command = intent.getExtras().getInt("command");
        switch (command) {
            case MusicPlayerFragment.RESUME:
                mediaPlayer.start();
                isPause = false;
                break;
            case MusicPlayerFragment.SEEK:
                mediaPlayer.seekTo(intent.getExtras().getInt(MusicPlayerFragment.SEEK_TO_TIME));
                break;
            case MusicPlayerFragment.START:
                Log.d("START", "sdfasfasfasdf");
                mediaPlayer.reset();
                sntAudioLink = intent.getExtras().getString("url");
                if (sntAudioLink != null && !sntAudioLink.isEmpty()) {
                    try {
                        mediaPlayer.setDataSource(sntAudioLink);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                    }
                    // if (!isPause) {
                    mediaPlayer.prepareAsync();
                    // }
                }
                break;
            case MusicPlayerFragment.UPDATE_STASTUS:
                Toast.makeText(this, "UPDATE", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MusicPlayerFragment.RESPONE);
                intent1.putExtra(MusicPlayerFragment.REPEAT_STATUS, repeatStatus);
                Toast.makeText(getApplicationContext(), "" + repeatStatus, Toast.LENGTH_SHORT).show();
//			intent.putStringArrayListExtra(MainActivity.LIST_SONG, listSong);
//			intent.putStringArrayListExtra(MainActivity.VALUES, values);
                intent1.putExtra(MusicPlayerFragment.PLAY, mediaPlayer.isPlaying());
                intent1.putExtra(MusicPlayerFragment.PAUSSE, isPause);
                intent1.putExtra(MusicPlayerFragment.RESPONE, MusicPlayerFragment.UPDATE);
                intent1.putExtra(MusicPlayerFragment.TIME_RESPONE,
                        mediaPlayer.getDuration() + "");
                sendBroadcast(intent1);
                updateCurrentTime();
                break;
            case MusicPlayerFragment.SAVE_LIST:
//			Toast.makeText(this, "SaveList", Toast.LENGTH_SHORT).show();

                listSong = new ArrayList<String>(intent.getStringArrayListExtra(MusicPlayerFragment.LIST_SONG));
//			if(listSong==null){
//			}
                values = new ArrayList<String>(intent.getStringArrayListExtra(MusicPlayerFragment.VALUES));
                break;
            case MusicPlayerFragment.STOP:
                mediaPlayer.pause();
                isPause = true;
                break;
            case MusicPlayerFragment.GET_TIME:

                break;

            case MusicPlayerFragment.SET_REPEAT:
                repeatStatus = intent.getExtras()
                        .getInt(MusicPlayerFragment.REPEAT_STATUS);
                Toast.makeText(getApplicationContext(), "" + repeatStatus, Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
        mHandler.postDelayed(UpdateSongTime, 100);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();

    }

    public void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this,
                        "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK" + extra,
                        Toast.LENGTH_SHORT).show();

                break;

            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, "MEDIA ERROR SERVER DIED" + extra,
                        Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "MEDIA ERROR UNKNOWN" + extra,
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // khong repeat
        switch (repeatStatus) {
            case 0:
                stopMedia();
                Intent intent = new Intent(MusicPlayerFragment.RESPONE);
                intent.putExtra(MusicPlayerFragment.RESPONE, MusicPlayerFragment.COMPLETE);
                sendBroadcast(intent);
                mediaPlayer.seekTo(0);
                break;
            case 2:

                break;
            case 1:
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
                break;
        }
    }

    private void stopMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();
        Intent intent11 = new Intent(MusicPlayerFragment.RESPONE);
        intent11.putExtra(MusicPlayerFragment.RESPONE, MusicPlayerFragment.FTIME);
        intent11.putExtra(MusicPlayerFragment.TIME_RESPONE, mediaPlayer.getDuration()
                + "");
        sendBroadcast(intent11);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            if (mediaPlayer.isPlaying()) {
                updateCurrentTime();
                mHandler.postDelayed(this, 100);
            }
        }
    };

    public void updateCurrentTime() {
        Intent intent1 = new Intent(MusicPlayerFragment.RESPONE);
        intent1.putExtra(MusicPlayerFragment.RESPONE, MusicPlayerFragment.TIME);
        intent1.putExtra(MusicPlayerFragment.TIME_RESPONE,
                mediaPlayer.getCurrentPosition() + "");
        sendBroadcast(intent1);

    }
}
