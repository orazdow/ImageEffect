package edu.bu.ollie.imageeffect;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class ImageWindowFragment extends Fragment {
    ImageView image;
    Bitmap img;
    ProcessActivity parentActivity;
    public ImageWindowFragment() {
    }

    void update(){

        Glide.with(getView()).applyDefaultRequestOptions(parentActivity.glideOptions).load(img).into(image);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_window, container, false);
        parentActivity = (ProcessActivity)getActivity();
        img = parentActivity.baseImage;
        image = view.findViewById(R.id.procImage);
        Glide.with(view).applyDefaultRequestOptions(parentActivity.glideOptions).load(img).into(image);
        return view;
    }

}
