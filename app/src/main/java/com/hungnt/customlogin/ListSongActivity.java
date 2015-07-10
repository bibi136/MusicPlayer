package com.hungnt.customlogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListSongActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView lvSong;
    ArrayList<SongInfo> songInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

        songInfos = new ArrayList<>();

        //Create song
        createSong();

        lvSong = (ListView) findViewById(R.id.lvSong);
        ListSong listSong = new ListSong();
        lvSong.setAdapter(listSong);

        lvSong.setOnItemClickListener(this);

    }

    private void createSong() {
        songInfos.add(new SongInfo("See You Again", "Wizkalifa"));
        songInfos.add(new SongInfo("Chandelier", "Sia"));
        songInfos.add(new SongInfo("Will It Rain", "Bruno Mars"));
        songInfos.add(new SongInfo("Good For You", "Selena"));
        songInfos.add(new SongInfo("Imagine", "Elton John",1123,4342,1215));
        songInfos.add(new SongInfo("Rocket Man", "John Lennon"));
        songInfos.add(new SongInfo("Moonlight Mile", "Rolling Stones",1122,3332,2233));
        songInfos.add(new SongInfo("American Pie", "Don McLean"));
        songInfos.add(new SongInfo("Your Song", "Elton John"));
        songInfos.add(new SongInfo("Everyday People", "Sly & The Family Stone"));
        songInfos.add(new SongInfo("Space Oddity", "David Bowie",2311,4443,235));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class ListSong extends BaseAdapter {


        @Override
        public int getCount() {
            return songInfos.size();
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
                view = getLayoutInflater().inflate(R.layout.layout_row, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
                viewHolder.tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
                viewHolder.tvLiked = (TextView) view.findViewById(R.id.tvLiked);
                viewHolder.tvListened = (TextView) view.findViewById(R.id.tvListened);
                viewHolder.tvDownloaded = (TextView) view.findViewById(R.id.tvDownloaded);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tvName.setText(songInfos.get(position).getName());
            viewHolder.tvAuthor.setText("by" + songInfos.get(position).getAuthor());
            viewHolder.tvLiked.setText(songInfos.get(position).getLiked()+"");
            viewHolder.tvDownloaded.setText(songInfos.get(position).getDownloaded()+"");
            viewHolder.tvListened.setText(songInfos.get(position).getListened()+"");
            return view;
        }

        private class ViewHolder {
            TextView tvName,tvAuthor,tvLiked,tvDownloaded,tvListened;
        }
    }
}
