package edu.bu.ollie.imageeffect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.bu.ollie.imageeffect.image.ToneCtlFragment;


public class ProcImageFragment extends Fragment {

    Integer index;
    Button procButton, revertButton;
    TextView effectLabel;
    ProcessActivity parentActivity;
    FragmentManager fragmentManager;
    ImageWindowFragment imgWindow;

    ToneCtlFragment toneCtlFragment;

    public ProcImageFragment() {
    }

    public void updateImgWindow(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imgWindow.update();
            }
        });
    }
    void addControlFragment(GlobalState.EffectMode mode){
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (mode){
            case TONE:
                toneCtlFragment = new ToneCtlFragment();
                transaction.replace(R.id.control_bar, toneCtlFragment);
                break;
        }

        transaction.commit();
    }

    void resetControls(GlobalState.EffectMode mode){
        switch (mode){
            case TONE:
                toneCtlFragment.resetControls();
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proc_image_view, container, false);
        parentActivity = (ProcessActivity)getActivity();
        fragmentManager = getFragmentManager();
        effectLabel = view.findViewById(R.id.effect_label);
        effectLabel.setText(parentActivity.mode.toString());
        imgWindow = new ImageWindowFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.imgWindow, imgWindow);
        transaction.commit();
        addControlFragment(parentActivity.mode);
        procButton = (Button)view.findViewById(R.id.procApply);
        revertButton = (Button)view.findViewById(R.id.procRevert);
        procButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.apply();
                resetControls(parentActivity.mode);
            }
        });
        revertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // parentActivity.previewViewReturn();
            }
        });
        return  view;
    }

}
