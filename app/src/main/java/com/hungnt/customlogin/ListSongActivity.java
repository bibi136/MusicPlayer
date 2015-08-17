package com.hungnt.customlogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.hungnt.customlogin.Objs.SongInfo;
import com.hungnt.customlogin.fragments.ListSongFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


public class ListSongActivity extends FragmentActivity {

    private static final String TAG = "hung";
    public ImageLoader imageLoader = ImageLoader.getInstance();

    public ArrayList<SongInfo> songs;
    public TextView tv_name_song_playing, tv_artist_playing_song;
    private ViewPager viewPager;
    public View layoutPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        songs = new ArrayList<>();

        //Create song
        createSong();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        tabs.setIndicatorColor(0xFF3F9FE0);
        tabs.setBackgroundColor(0xCC5c5b78);
        tabs.setTextColor(0xffffffff);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ListPagerAdapter pagerAdapter = new ListPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        tabs.setViewPager(viewPager);

        tv_name_song_playing = (TextView) findViewById(R.id.tv_name_playing_song);
        tv_artist_playing_song = (TextView) findViewById(R.id.tv_artist_playing_song);
        layoutPlaying = findViewById(R.id.layout_playing);
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
            ListSongFragment listSongFragment = new ListSongFragment();
            return listSongFragment;
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

    private void createSong() {
        songs.add(new SongInfo("See You Again", "Wizkalifa", 444, 2321, 12313, R.drawable.artwork_native));
        songs.add(new SongInfo("Chandelier", "Sia", 44, 55, 33, R.drawable.artwork_yearendhot));
        songs.add(new SongInfo("Will It Rain", "Bruno Mars,", 1234, 4566, 23422, R.drawable.artwork_hooligans));
        songs.add(new SongInfo("Good For You", "Selena", 123, 434, 1123, R.drawable.artwork_maron5));
        songs.add(new SongInfo("Imagine", "Elton John", 1123, 4342, 1215, R.drawable.artwork_fifty));
        songs.add(new SongInfo("Rocket Man", "John Lennon"));
        songs.add(new SongInfo("Moonlight Mile", "Rolling Stones", 1122, 3332, 2233, R.drawable.artwork_hotel));
        songs.add(new SongInfo("American Pie", "Don McLean"));
        songs.add(new SongInfo("Your Song", "Elton John", 1, 43, 555, R.drawable.artwork_rockstar));
        songs.add(new SongInfo("Everyday People", "Sly & The Family Stone"));
        songs.add(new SongInfo("Space Oddity", "David Bowie", 2311, 4443, 235, R.drawable.artwork_yeu));
    }

}
