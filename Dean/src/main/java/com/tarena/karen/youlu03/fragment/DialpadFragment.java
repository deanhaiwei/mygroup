package com.tarena.karen.youlu03.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tarena.karen.youlu03.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialpadFragment extends Fragment {


    public DialpadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dailpad_layout,container,false);
        return view;
    }

}
