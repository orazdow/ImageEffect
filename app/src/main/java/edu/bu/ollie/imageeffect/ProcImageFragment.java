package edu.bu.ollie.imageeffect;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import edu.bu.ollie.imageeffect.image.ToneCtlFragment;


public class ProcImageFragment extends Fragment {

    Integer index;
    Button procButton, revertButton;
    TextView effectLabel;
    ProcessActivity parentActivity;
    FragmentManager fragmentManager;
    ImageView image;
    Bitmap img;
    ToneCtlFragment toneCtlFragment;

    public ProcImageFragment() {
    }

    public void updateImgWindow(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(getView()).applyDefaultRequestOptions(parentActivity.glideOptions).load(img).into(image);
            }
        });
    }
    void addControlFragment(Global.EffectMode mode){
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (mode){
            case TONE:
                toneCtlFragment = new ToneCtlFragment();
                transaction.replace(R.id.control_bar, toneCtlFragment);
                break;
        }

        transaction.commit();
    }

    void resetControls(Global.EffectMode mode){
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
        image = view.findViewById(R.id.procImage);
        img = parentActivity.baseImage;
        Glide.with(view).applyDefaultRequestOptions(parentActivity.glideOptions).load(img).into(image);
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
