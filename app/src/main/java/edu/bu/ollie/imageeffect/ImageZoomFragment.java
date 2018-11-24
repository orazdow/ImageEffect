package edu.bu.ollie.imageeffect;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class ImageZoomFragment extends Fragment {

    ImageView image;
    int index = 0;
    Button processButton;
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
                    .load(GlobalState.imagePaths.get(GlobalState.currentIndex)).into(image);
        }
    }

    // switch image on swime
    void changeImg(){
        if(swipeDir == SwipeDir.LEFT){
            index = index-1 < 0 ? GlobalState.imagePaths.size()-1 : index-1;
            GlobalState.currentIndex = index;
            Glide.with(getView()).load(GlobalState.imagePaths.get(index)).into(image);
            parentActivity.imgModified = false;
        }else if(swipeDir == SwipeDir.RIGHT){
            index = (index+1)%GlobalState.imagePaths.size();
            GlobalState.currentIndex = index;
            Glide.with(getView()).load(GlobalState.imagePaths.get(index)).into(image);
            parentActivity.imgModified = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_zoom, container, false);
        parentActivity = (ProcessActivity) getActivity();
        index = GlobalState.currentIndex;
        changeDialog = new ChangeDialog();
        tabLayout = view.findViewById(R.id.effectTabs);
        tabLayout.setScrollPosition(parentActivity.mode.ordinal(), 0, true);

        // effect selector tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                parentActivity.mode = GlobalState.EffectMode.values()[tab.getPosition()];
                tabLayout.setScrollPosition(tab.getPosition(),0,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                parentActivity.mode = GlobalState.EffectMode.values()[tab.getPosition()];
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
        return view;
    }

}
