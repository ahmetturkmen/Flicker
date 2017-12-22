package com.example.geek.flicker;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by geek on 12/22/17.
 */

public class FlickerAdapter extends RecyclerView.Adapter<FlickerAdapter.ViewHolder> {


    private List<PhotoData> mPhotosList;
    private Context mContext;
    private String[] mDataset;

    public FlickerAdapter(Context context,List<PhotoData> photoDataList) {
        mPhotosList=photoDataList;
        mContext=context;
    }

    // Create new views (invoked by the layout manager)

    @Override
    public FlickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_cardview_layout,parent,false);
        FlickerAdapter.ViewHolder viewHolder = new FlickerAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemCount() {

        return ((mPhotosList != null) && (mPhotosList.size() !=0) ? mPhotosList.size() : 0);
    }

    @Override
    public void onBindViewHolder(FlickerAdapter.ViewHolder holder, int position) {

        PhotoData photoItem = mPhotosList.get(position);

        //picasso used to getting and inserting image to thumbnail of a imageview

//        holder.thumbnail.setImageResource(photoItem.getResourceID());
        Picasso.with(mContext).load(photoItem.getImage())
                .error(R.drawable.ic_error_black_24dp)
                .placeholder(R.drawable.place_holder_icon)
                .into(holder.thumbnail);

//        holder.photoTitle.setText(photoItem.getTitle());


    }

    public PhotoData getPhoto(int position) {
        return ((mPhotosList != null) && (mPhotosList.size() !=0) ? mPhotosList.get(position) : null);
    }

    void setPhotoData(List<PhotoData> newPhotos){
        mPhotosList=newPhotos;
        notifyDataSetChanged();
    }



    class ViewHolder extends  RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView  photoTitle;

        // each data item is just a string in this case

        public ViewHolder(View itemView) {
            super(itemView);
            this.thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);
//            this.photoTitle=(TextView)itemView.findViewById(R.id.photoTitle);

        }

    }
}

