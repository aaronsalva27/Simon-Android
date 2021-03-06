package edu.fje.dam.simon.SimonView;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.fje.dam.simon.Adapters.ImageAdapter;
import edu.fje.dam.simon.R;


public class SimonTableFragment extends Fragment {
    // adaptador del tablero
    ArrayAdapter<Image> Adapter;
    GridView figuresGrid;
    public Context context;

    int images[] = {
            R.drawable.quadrado_red,
            R.drawable.quadrado_blue,
            R.drawable.quadrado_green,
            R.drawable.quadrado_yellow,

            R.drawable.redonda_red,
            R.drawable.redonda_blue,
            R.drawable.redonda_green,
            R.drawable.redonda_yellow,

            R.drawable.rombo_red,
            R.drawable.rombo_blue,
            R.drawable.rombo_green,
            R.drawable.rombo_yellow
    };

    public SimonTableFragment () {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simon_table, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        figuresGrid = (GridView) getActivity().findViewById(R.id.figuresGrid);

        figuresGrid.setAdapter(new ImageAdapter(getContext(),images));
        //figuresGrid.setAdapter(Adapter);

        figuresGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                Context context = getActivity().getApplicationContext();

                Toast.makeText( context,"pic" + (position + 1) + " selected",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context = getContext();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


}


