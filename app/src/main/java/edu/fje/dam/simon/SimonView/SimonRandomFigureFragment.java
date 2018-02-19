package edu.fje.dam.simon.SimonView;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.fje.dam.simon.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SimonRandomFigureFragment extends Fragment {


    public SimonRandomFigureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simon_random_figure, container, false);
    }

}
