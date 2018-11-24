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

    public ProcImageFragment() {
    }

    public void updateImgWindow(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.imgWindow, new ImageWindowFragment());
        transaction.commit();
    }
    void addControlFragment(GlobalState.EffectMode mode){
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (mode){
            case TONE:
                transaction.replace(R.id.control_bar, new ToneCtlFragment());
                break;

        }

        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proc_image_view, container, false);
        parentActivity = (ProcessActivity)getActivity();
        fragmentManager = getFragmentManager();
        effectLabel = view.findViewById(R.id.effect_label);
        effectLabel.setText(parentActivity.mode.toString());
        updateImgWindow();
        addControlFragment(parentActivity.mode);
        procButton = (Button)view.findViewById(R.id.procApply);
        revertButton = (Button)view.findViewById(R.id.procRevert);
        procButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.procImg();
                updateImgWindow();
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
