package edu.fje.dam.simon.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



/**
 * Clase que extiende de adapter y devuelve una grid con images
 */
public class ImageAdapter extends BaseAdapter
{
    // clase donde se va ha utilitzar el adaptador
    private Context context;
    // lista de imagenes que se van a devolve
    private int images[];

    /**
     * Constuctor
     * @param c clase donde se va a utilizar el adapter
     * @param images lista de imagenes a devoler
     */
    public ImageAdapter(Context c, int[] images)
    {
        context = c;
        this.images = images;
    }

    // Devuelve el numero de imagenes
    public int getCount() {
        return images.length;
    }

    // Devuelce el ID de un item
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // Devuelve un image view
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

