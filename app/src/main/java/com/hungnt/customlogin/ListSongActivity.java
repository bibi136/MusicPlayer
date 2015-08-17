package com.hungnt.customlogin;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.hungnt.customlogin.Objs.AlbumInfo;
import com.hungnt.customlogin.Objs.SongInfo;
import com.hungnt.customlogin.fragments.ListAlbumFragment;
import com.hungnt.customlogin.fragments.ListSongFragment;
import com.hungnt.customlogin.fragments.MusicPlayerFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


public class ListSongActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "hung";
    public ImageLoader imageLoader = ImageLoader.getInstance();
    private boolean isPlaying;
    private int pos = 0;

    public ArrayList<SongInfo> songs;
    public ArrayList<AlbumInfo> albums = new ArrayList<>();
    public TextView tv_name_song_playing, tv_artist_playing_song;
    private ViewPager viewPager;
    public View layoutPlaying;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        songs = new ArrayList<>();

        findAllMusic(0, 0);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                findAllAlbum();
            }
        });
        thread1.start();

        //Create song
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColor(0xffffffff);
        tabs.setIndicatorHeight(10);
        tabs.setBackgroundColor(0xff4a2a71);
        tabs.setTextColor(0xffffffff);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ListPagerAdapter pagerAdapter = new ListPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        tabs.setViewPager(viewPager);

        tv_name_song_playing = (TextView) findViewById(R.id.tv_name_playing_song);
        tv_artist_playing_song = (TextView) findViewById(R.id.tv_artist_playing_song);
        layoutPlaying = findViewById(R.id.layout_playing);
        layoutPlaying.setOnClickListener(this);

        if (MusicPlayerFragment.isPlaying) {
            layoutPlaying.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        bundle.putBoolean("play", false);

        MusicPlayerFragment playerFragment = new MusicPlayerFragment();
        playerFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.mainLayout, playerFragment).addToBackStack(null).commit();
    }

    public void findAllMusic1() {
        Cursor cursor, cursor2;
        String[] STAR = {"*"};
        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri allalbumuri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        cursor = getApplicationContext().getContentResolver().query(
                allsongsuri, STAR, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String cover = null;
                do {
                    int songAlbumID = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    cursor2 = getApplicationContext().getContentResolver()
                            .query(allalbumuri, STAR, null, null, null);
                    if (cursor2 != null) {
                        if (cursor2.moveToFirst()) {
                            do {
                                int albumid = cursor2
                                        .getInt(cursor2
                                                .getColumnIndex(MediaStore.Audio.Albums._ID));
                                if (albumid == songAlbumID) {
                                    cover = cursor2
                                            .getString(cursor2
                                                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                                }
                            } while (cursor2.moveToNext());
                        }
                    }
                    cursor2.close();
                    SongInfo song = new SongInfo(
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)).split("\\.")[0],
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DATA)),
                            cover,
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                            cursor.getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)),
                            cursor.getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                    songs.add(song);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    public void findAllMusic(int filter, int id) {
        Cursor cursor, cursor2;
        String[] STAR = {"*"};
        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri allalbumuri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        songs.clear();
        cursor = getApplicationContext().getContentResolver().query(
                allsongsuri, STAR, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String cover = null;
                do {
                    int songAlbumID = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    cursor2 = getApplicationContext().getContentResolver()
                            .query(allalbumuri, STAR, null, null, null);
                    if (cursor2 != null) {
                        if (cursor2.moveToFirst()) {
                            do {
                                int albumid = cursor2
                                        .getInt(cursor2
                                                .getColumnIndex(MediaStore.Audio.Albums._ID));
                                if (albumid == songAlbumID) {
                                    cover = cursor2
                                            .getString(cursor2
                                                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                                }
                            } while (cursor2.moveToNext());
                        }
                    }
                    cursor2.close();
                    SongInfo song = new SongInfo(
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DATA)),
                            cover,
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                            cursor.getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)),
                            cursor.getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                    switch (filter) {
                        case 1:/* loc theo album */
                            if (song.getIdAlbum() == id) {
                                songs.add(song);
                            }
                            break;
                        case 2:/* loc theo Artist */
                            if (song.getIdArtist() == id) {
                                songs.add(song);
                            }
                            break;
                        default:
                            songs.add(song);
                            break;
                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    public void findAllAlbum() {
        String[] STAR = {"*"};
        Uri allsongsuri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        cursor = getApplicationContext().getContentResolver().query(
                allsongsuri, STAR, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    AlbumInfo album = new AlbumInfo(
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM)),
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)),
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            cursor.getInt(cursor
                                    .getColumnIndex(MediaStore.Audio.Albums._ID)));
                    albums.add(album);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }


    private class ListPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"SONGS", "ARTISTS", "ALBUMS",};

        public ListPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment listSongFragment = null;

            if (position == 0) {
                listSongFragment = new ListSongFragment();
            } else if (position == 1) {
                listSongFragment = new ListSongFragment();

            } else if (position == 2) {
                listSongFragment = new ListAlbumFragment();

            }
            return listSongFragment;
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

}
