package edu.bu.ollie.imageeffect.image;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import edu.bu.ollie.imageeffect.R;


public class ColorCtlFragment extends CtlFragment {

    SeekBar hueBar, satBar;

    public ColorCtlFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_ctl, container, false);
        hueBar = view.findViewById(R.id.hueBar);
        satBar = view.findViewById(R.id.satBar);
        resetControls();
        onCreateInit();
        hueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                processor.setcolorHue(seekBar.getProgress()-128);
                handleUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        satBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                processor.setcolorSat(seekBar.getProgress()-128);
                handleUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return  view;
    }

    @Override
    public void resetControls() {
        hueBar.setProgress(128);
        satBar.setProgress(128);
    }
}
