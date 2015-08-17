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
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = mainActivity.getLayoutInflater();
            view = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.tvArtist = (TextView) view.findViewById(R.id.tvAuthor);
            viewHolder.image = (ImageView) view.findViewById(R.id.ivArtwork);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvName.setText(listSongs.get(position).getAlbumName());
        viewHolder.tvArtist.setText(listSongs.get(position).getArtist());
        String url = mainActivity.songs.get(position).getCover();
        mainActivity.imageLoader.displayImage("file://" + url, viewHolder.image, options, animateFirstListener);

        return view;
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

    static class ViewHolder {
        TextView tvName, tvArtist;
        ImageView image;
    }
}
