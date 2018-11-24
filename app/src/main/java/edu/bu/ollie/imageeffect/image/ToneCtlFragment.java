package edu.bu.ollie.imageeffect.image;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.bu.ollie.imageeffect.ProcImageFragment;
import edu.bu.ollie.imageeffect.ProcessActivity;
import edu.bu.ollie.imageeffect.R;
import edu.bu.ollie.imageeffect.TestProcessor;


public class ToneCtlFragment extends Fragment {

    SeekBar brightnessBar, contrastBar, gammaBar;
    int brightness, contrast, gamma;
    ProcessActivity parentActivity;
    ProcImageFragment parentFragment;
    TestProcessor processor;

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
        parentActivity = (ProcessActivity) getActivity();
        parentFragment = parentActivity.procViewFragment;
        processor = parentActivity.testprocessor;

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                processor.setToneBrightness(seekBar.getProgress());
                processor.preview();
                parentFragment.updateImgWindow();
                Log.i("brighntess bar", "stop tracking: "+seekBar.getProgress());
            }
        });

        contrastBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                processor.setToneContrast(seekBar.getProgress());
                processor.preview();
                parentFragment.updateImgWindow();
                Log.i("contrast bar", "stop tracking: "+seekBar.getProgress());
            }
        });

        gammaBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                processor.setToneGamma(seekBar.getProgress());
                processor.preview();
                parentFragment.updateImgWindow();
                Log.i("gamma bar", "stop tracking: "+seekBar.getProgress());
            }
        });
        return view;
    }

}
