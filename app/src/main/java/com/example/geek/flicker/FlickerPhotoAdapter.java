package com.example.geek.flicker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geek.flicker.DB.FlickerDbHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by geek on 12/22/17.
 */

public class FlickerPhotoAdapter extends RecyclerView.Adapter<FlickerPhotoAdapter.ViewHolder> {


    private Context mContext;
    private ListItemOnClickListener mOnClickListener;
    private Cursor dataCursor;

    public FlickerPhotoAdapter(Context context, ListItemOnClickListener mOnClickListener) {
        mContext=context;
        this.mOnClickListener=mOnClickListener;

    }

    // Create new views (invoked by the layout manager)

    @Override
    public FlickerPhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_cardview_layout,parent,false);
        FlickerPhotoAdapter.ViewHolder viewHolder = new FlickerPhotoAdapter.ViewHolder(view,mOnClickListener);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }


    @Override
    public void onBindViewHolder(FlickerPhotoAdapter.ViewHolder holder, int position) {
        dataCursor.moveToPosition(position);

        String imageURL = dataCursor.getString(4);


//        PhotoData photoItem = mPhotosList.get(position);

        //picasso used to getting and inserting image to thumbnail of a imageview

//        holder.thumbnail.setImageResource(photoItem.getResourceID());
        Picasso.with(mContext).load(imageURL)
                .error(R.drawable.ic_error_black_24dp)
                .placeholder(R.drawable.place_holder_icon)
                .into(holder.thumbnail);

//        holder.photoTitle.setText(photoItem.getTitle());


    }


    public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView thumbnail;
        private  ListItemOnClickListener mListener;

        TextView  photoTitle;

        // each data item is just a string in this case

        public ViewHolder(View itemView,ListItemOnClickListener mListener) {
            super(itemView);
            this.thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);
            this.mListener=mListener;
//            this.photoTitle=(TextView)itemView.findViewById(R.id.photoTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListener.onListItemClick(clickedPosition);
        }
    }
}

