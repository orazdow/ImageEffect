package edu.bu.ollie.imageeffect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GalleryFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter galleryAdapter;
    RecyclerView.LayoutManager layoutManager;

    public GalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        galleryAdapter = new GalleryViewAdapter(GlobalState.imagePaths);
        recyclerView.setAdapter(galleryAdapter);
        return view;
    }

}
