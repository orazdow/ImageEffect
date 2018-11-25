package edu.bu.ollie.imageeffect.image;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import edu.bu.ollie.imageeffect.R;


public class ToneCtlFragment extends CtlFragment {

    SeekBar brightnessBar, contrastBar, gammaBar;

    public ToneCtlFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tone_ctl, container, false);
        brightnessBar = view.findViewById(R.id.brightnessBar);
        contrastBar = view.findViewById(R.id.contrastBar);
        gammaBar = view.findViewById(R.id.gammaBar);
        onCreateInit();

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                processor.setToneBrightness(seekBar.getProgress());
                handleUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        contrastBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                processor.setToneContrast(seekBar.getProgress());
                handleUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        gammaBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                processor.setToneGamma(seekBar.getProgress());
                handleUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }

    @Override
    public void resetControls() {
        brightnessBar.setProgress(0);
        contrastBar.setProgress(0);
        gammaBar.setProgress(0);
    }
}
