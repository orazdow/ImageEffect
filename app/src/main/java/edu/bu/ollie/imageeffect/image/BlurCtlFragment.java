package edu.bu.ollie.imageeffect.image;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.bu.ollie.imageeffect.R;

public class BlurCtlFragment extends CtlFragment {

    Button b;


    public BlurCtlFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blur_ctl, container, false);
        b = view.findViewById(R.id.convButton);
        onCreateInit();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUpdate();
            }
        });
        return view;
    }

    @Override
    public void resetControls() {

    }
}
