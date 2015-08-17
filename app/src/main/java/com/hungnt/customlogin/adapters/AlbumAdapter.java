package com.hungnt.customlogin.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungnt.customlogin.ListSongActivity;
import com.hungnt.customlogin.Objs.AlbumInfo;
import com.hungnt.customlogin.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hoang on 8/12/2015.
 */
public class AlbumAdapter extends ArrayAdapter<AlbumInfo> {
    private ListSongActivity mainActivity;
    private int resource;
    private ArrayList<AlbumInfo> listSongs;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public AlbumAdapter(ListSongActivity context, int resource, ArrayList<AlbumInfo> objects) {
        super(context, resource, objects);
        mainActivity = context;
        listSongs = objects;
        this.resource = resource;


        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_artwork)
                .showImageForEmptyUri(R.drawable.img_artwork)
                .showImageOnFail(R.drawable.img_artwork)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = mainActivity.getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        TextView tv_song = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
        ImageView img_artwork = (ImageView) convertView.findViewById(R.id.ivArtwork);

        tv_song.setText(listSongs.get(position).getAlbumName());
        tvAuthor.setText(listSongs.get(position).getArtist());
        String url = mainActivity.songs.get(position).getCover();
        if (url != null) {
            mainActivity.imageLoader.displayImage("file://" + url, img_artwork, options, animateFirstListener);
        }

        return convertView;
    }

    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
