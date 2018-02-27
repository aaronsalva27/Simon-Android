package edu.fje.dam.simon.Services;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import edu.fje.dam.simon.R;
import edu.fje.dam.simon.TableActivity;

public class AudioTaskctivity extends AppCompatActivity {

    public AudioTask task;
    private static MediaPlayer mp;
    private AudioManager am;
    private String LOG = "edu.fje.dam2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mp = MediaPlayer.create(this, R.raw.background_music);
        mp.setLooping(true);

        Log.d(LOG, "Intent Created");

        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        int requestResult = am.requestAudioFocus(
                mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mp.start();

            Log.d(LOG, "audioFocus listener aconseguit amb èxit");

        } else if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            mp.stop();
        } else {
            Log.d(LOG, "error en la petició del listener de focus ");
        }
        task = new AudioTask();
        task.execute("start");

    }

    /**
     * Classe interna que gestiona tots els canvis d'estat del AudioFocus.
     * Es tracta d'un listener per a determinats canvis d'audio
     */
    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                //perdem el focus per exemple, una altre reproductor de música
                case AudioManager.AUDIOFOCUS_LOSS:
                    mp.stop();
                    Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_LOSS");
                    mp.release();
                    mp = null;
                    break;
                //perdem el focus temporalement, per exemple, trucada
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (mp.isPlaying())
                        mp.pause();

                    Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_LOSS_TRANSIENT");

                    break;
                //baixem el volum temporalment
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mp.setVolume(0.5f, 0.5f);
                    Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    break;

                //es recupera el focus d'audio
                case AudioManager.AUDIOFOCUS_GAIN:
                    mp.start();
                    mp.setVolume(1.0f, 1.0f);
                    Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_GAIN");
                    break;

                default:
                    Log.e(LOG, "codi desconegut");
            }
        }
    };


    /**
     * Servei implementat mitjançant una herència d'AsyncTask
     * @author sergi.grau@fje.edu
     * @version 1.0 18.02.2018
     */
    public class AudioTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mp.start();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("Sava",params[0].toString());
            return null;
        }

        @Override
        public void onProgressUpdate(String... values) {
            super.onProgressUpdate(String.valueOf(values));
            Log.d("Sava",values[0].toString());
            switch (values[0]){
                case "inici" : mp.start();
                    Log.d(LOG, "Audio Start");
                    break;
                case "pausa" : mp.pause();
                    Log.d(LOG, "Audio Pause");
                    break;
                default:
                    break;
            }

        }

        @Override
        protected void onPostExecute(String resultat) {

        }
    }
}
