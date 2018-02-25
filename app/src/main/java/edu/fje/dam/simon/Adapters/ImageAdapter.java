package edu.fje.dam.simon.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by sava on 25/02/18.
 */


public class ImageAdapter extends BaseAdapter
{
    private Context context;
    private int images[];

    public ImageAdapter(Context c, int[] images)
    {
        context = c;
        this.images = images;
    }

    //---returns the number of images---
    public int getCount() {
        return images.length;
    }

    //---returns the ID of an item---
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    //---returns an ImageView view---
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(images[position]);
        return imageView;
    }
}

