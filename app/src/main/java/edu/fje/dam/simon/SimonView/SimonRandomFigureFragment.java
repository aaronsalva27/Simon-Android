package edu.fje.dam.simon.SimonView;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.fje.dam.simon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimonRandomFigureFragment extends Fragment {
    private TextView text;
    private ImageView randomImg;
    private Communicator comm = null;

    public SimonRandomFigureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simon_random_figure, container, false);
    }

    public void newFigure(String newText) {
        text = (TextView) getActivity().findViewById(R.id.text);
        text.setText(newText);
        comm = (Communicator) getActivity();
        comm.randomToMain("un puto mensaje lol");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        randomImg = (ImageView) getActivity().findViewById(R.id.randomImg);
    }
}
