package edu.bu.ollie.imageeffect;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class ImageZoomFragment extends Fragment {

    ImageView image;
    int index = 0;
    Button processButton, saveButton;
    TabLayout tabLayout;
    ProcessActivity parentActivity;
    FragmentManager fragManager;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    enum SwipeDir{NONE, LEFT, RIGHT};
    SwipeDir swipeDir = SwipeDir.NONE;
    ChangeDialog changeDialog;

    public ImageZoomFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        displayImage(getView());
    }

    // display from mediastore or processor
    void displayImage(View view){
        if(parentActivity.imgModified){
            Glide.with(view).load(parentActivity.baseImage).into(image);
        }else {
            Glide.with(view).applyDefaultRequestOptions(parentActivity.glideOptions)
                    .load(Global.imagePaths.get(Global.currentIndex)).into(image);
        }
    }

    // switch image on swime
    void changeImg(){
        if(swipeDir == SwipeDir.LEFT){
            index = index-1 < 0 ? Global.imagePaths.size()-1 : index-1;
        }else if(swipeDir == SwipeDir.RIGHT){
            index = (index+1)%Global.imagePaths.size();
        }
        Global.currentIndex = index;
        Glide.with(getView()).load(Global.imagePaths.get(index)).into(image);
        parentActivity.imgModified = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_zoom, container, false);
        parentActivity = (ProcessActivity)getActivity();
        index = Global.currentIndex;
        changeDialog = new ChangeDialog();
        tabLayout = view.findViewById(R.id.effectTabs);
        tabLayout.setScrollPosition(parentActivity.mode.ordinal(), 0, true);

        // effect selector tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                parentActivity.mode = Global.EffectMode.values()[tab.getPosition()];
                tabLayout.setScrollPosition(tab.getPosition(),0,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                parentActivity.mode = Global.EffectMode.values()[tab.getPosition()];
                tabLayout.setScrollPosition(tab.getPosition(),0,true);
            }
        });


        // swipe listener
        // https://stackoverflow.com/questions/6645537/how-to-detect-the-swipe-left-or-right-in-android
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch(action)
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if(x2 < x1){
                            //left
                            if(Math.abs(x2-x1) > MIN_DISTANCE){
                                swipeDir = SwipeDir.LEFT;
                                if(!parentActivity.imgModified){
                                    changeImg();
                                }else {
                                    changeDialog.show(getFragmentManager(), "LEFT");
                                }
                            }
                        }else {
                            //right
                            if(Math.abs(x2-x1) > MIN_DISTANCE){
                                swipeDir = SwipeDir.RIGHT;
                                if(!parentActivity.imgModified){
                                    changeImg();
                                }else {
                                    changeDialog.show(getFragmentManager(), "RIGHT");
                                }
                            }
                        }
                        return  true;
                }
                return false;

            }
        });
        fragManager = getFragmentManager();
        image = (ImageView)view.findViewById(R.id.zoomImage);
        displayImage(view);
        processButton = (Button)view.findViewById(R.id.processButton);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    parentActivity.toProcView();
            }
        });
        saveButton = (Button)view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rtn = parentActivity.save();
                if(rtn.equalsIgnoreCase("null")){
                    Toast.makeText(getContext(), "error saving file", Toast.LENGTH_SHORT).show();
                }
                else if(rtn.equalsIgnoreCase("nochange")){
                    Toast.makeText(getContext(), "no changes to save", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), rtn+" saved succesfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        if(parentActivity.imgModified){
            saveButton.setAlpha(1f);
            saveButton.setClickable(true);
        }else {
            saveButton.setAlpha(0.5f);
            saveButton.setClickable(false);        }
    }
}
