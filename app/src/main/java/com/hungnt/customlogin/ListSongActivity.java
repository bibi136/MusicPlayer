package com.hungnt.customlogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.hungnt.customlogin.Objs.SongInfo;
import com.hungnt.customlogin.fragments.ListSongFragment;
import com.hungnt.customlogin.fragments.MusicPlayerFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


public class ListSongActivity extends FragmentActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "hung";
    public ImageLoader imageLoader = ImageLoader.getInstance();

    public ArrayList<SongInfo> songs;
    private TextView tvCount;
    private ViewPager viewPager;

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

        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCount.setText(songs.size() + " songs in Queue");

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
//        for (int i = 0; i < 50; i++) {
//            songs.add(new SongInfo("See You Again", "Wizkalifa", 444, 2321, 12313, R.drawable.img_artwork));
//        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //TODO bat su kien play nhac khi chon 1 bai hat
        Log.d(TAG, "onItemClick ");

        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);

        MusicPlayerFragment playerFragment = new MusicPlayerFragment();
        playerFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.mainLayout, playerFragment).addToBackStack(null).commit();

    }


    //Adapter
    class ListSong extends BaseAdapter {


        @Override
        public int getCount() {
            return songs.size();
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
            ViewHolder viewHolder;

            if (view == null) {

                //Inflate layout
                view = getLayoutInflater().inflate(R.layout.item_listview_song, parent, false);

                //set new viewHolder
                viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
                viewHolder.tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
//                viewHolder.tvLiked = (TextView) view.findViewById(R.id.tvLiked);
//                viewHolder.tvListened = (TextView) view.findViewById(R.id.tvListened);
//                viewHolder.tvDownloaded = (TextView) view.findViewById(R.id.tvDownloaded);
//                viewHolder.ivArtwork = (ImageView) view.findViewById(R.id.ivArtwork);

                //store the holder
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            SongInfo songInfo = songs.get(position);

            if (songInfo != null) {
                viewHolder.tvName.setText(songInfo.getName());
                viewHolder.tvAuthor.setText(songInfo.getAuthor());
//                viewHolder.tvLiked.setText(songInfo.getLiked() + "");
//                viewHolder.tvDownloaded.setText(songInfo.getDownloaded() + "");
//                viewHolder.tvListened.setText(songInfo.getListened() + "");
//                viewHolder.ivArtwork.setImageResource(songInfo.getCover());
            }
            return view;
        }

    }

    static class ViewHolder {
        TextView tvName, tvAuthor, tvLiked, tvDownloaded, tvListened;
        ImageView ivArtwork;
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
}
