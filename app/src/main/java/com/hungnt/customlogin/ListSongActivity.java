package com.hungnt.customlogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListSongActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView lvSong;
    ArrayList<SongInfo> songIfs;
    TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

        songIfs = new ArrayList<>();

        //Create song
        createSong();

        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCount.setText(songIfs.size() + " songs in Queue");

        lvSong = (ListView) findViewById(R.id.lvSong);
        ListSong listSong = new ListSong();
        lvSong.setAdapter(listSong);

        lvSong.setOnItemClickListener(this);

    }

    private void createSong() {
        songIfs.add(new SongInfo("See You Again", "Wizkalifa", 444, 2321, 12313, R.drawable.artwork_native));
        songIfs.add(new SongInfo("Chandelier", "Sia", 44, 55, 33, R.drawable.artwork_yearendhot));
        songIfs.add(new SongInfo("Will It Rain", "Bruno Mars,", 1234, 4566, 23422, R.drawable.artwork_hooligans));
        songIfs.add(new SongInfo("Good For You", "Selena", 123, 434, 1123, R.drawable.artwork_maron5));
        songIfs.add(new SongInfo("Imagine", "Elton John", 1123, 4342, 1215, R.drawable.artwork_fifty));
        songIfs.add(new SongInfo("Rocket Man", "John Lennon"));
        songIfs.add(new SongInfo("Moonlight Mile", "Rolling Stones", 1122, 3332, 2233, R.drawable.artwork_hotel));
        songIfs.add(new SongInfo("American Pie", "Don McLean"));
        songIfs.add(new SongInfo("Your Song", "Elton John", 1, 43, 555, R.drawable.artwork_rockstar));
        songIfs.add(new SongInfo("Everyday People", "Sly & The Family Stone"));
        songIfs.add(new SongInfo("Space Oddity", "David Bowie", 2311, 4443, 235, R.drawable.artwork_yeu));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class ListSong extends BaseAdapter {


        @Override
        public int getCount() {
            return songIfs.size();
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
                view = getLayoutInflater().inflate(R.layout.layout_row, parent, false);

                //set new viewHolder
                viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
                viewHolder.tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
                viewHolder.tvLiked = (TextView) view.findViewById(R.id.tvLiked);
                viewHolder.tvListened = (TextView) view.findViewById(R.id.tvListened);
                viewHolder.tvDownloaded = (TextView) view.findViewById(R.id.tvDownloaded);
                viewHolder.ivArtwork = (ImageView) view.findViewById(R.id.ivArtwork);

                //store the holder
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            SongInfo songInfo = songIfs.get(position);

            if(songInfo!=null){
                viewHolder.tvName.setText(songInfo.getName());
                viewHolder.tvAuthor.setText("by " + songInfo.getAuthor());
                viewHolder.tvLiked.setText(songInfo.getLiked() + "");
                viewHolder.tvDownloaded.setText(songInfo.getDownloaded() + "");
                viewHolder.tvListened.setText(songInfo.getListened() + "");
                viewHolder.ivArtwork.setImageResource(songInfo.getCover());
            }
            return view;
        }

    }


    static class ViewHolder {
        TextView tvName, tvAuthor, tvLiked, tvDownloaded, tvListened;
        ImageView ivArtwork;
    }
}
