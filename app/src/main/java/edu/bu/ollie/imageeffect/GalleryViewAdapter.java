package edu.bu.ollie.imageeffect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.GalleryViewHolder>{

    private ArrayList<String> imgPaths;
    private MainActivity context;

    public GalleryViewAdapter(ArrayList<String> paths){
        imgPaths = paths;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = (MainActivity) viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.gallery_item, viewGroup, false);
        GalleryViewAdapter.GalleryViewHolder viewHolder = new GalleryViewAdapter.GalleryViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder galleryViewHolder, int pos) {
        String uri = imgPaths.get(pos);
        galleryViewHolder.index = pos;
        Glide.with(context)
                .load(uri)
                .into(galleryViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return imgPaths.size();
    }


    protected class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected ImageView image;
        Integer index;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(index != null){
                context.zoomImage(index);
            }
        }
    }

}
