package edu.fje.dam.simon.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import edu.fje.dam.simon.R;

/**
 * Created by sava on 27/02/18.
 */

public class SoundsUtility extends AppCompatActivity {
    static MediaPlayer sound = null;

    public enum Type {
        RED,
        BLUE,
        GREEN,
        YELLOW,
        ERROR
    }

    public static void playSound(Type type, Context context) {
        switch (type) {
            case RED:
                sound= MediaPlayer.create(context, R.raw.sfx_sounds_error1);
                break;
            case BLUE:
                sound= MediaPlayer.create(context, R.raw.sfx_sounds_error1);
                break;
            case GREEN:
                sound= MediaPlayer.create(context, R.raw.sfx_sounds_error1);
                break;
            case YELLOW:
                sound= MediaPlayer.create(context, R.raw.sfx_sounds_error1);
                break;
            case ERROR:
                sound= MediaPlayer.create(context, R.raw.sfx_sounds_error1);
                break;
        }
    }

}
