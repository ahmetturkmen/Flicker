package com.example.geek.flicker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geek.flicker.DB.FlickerContract;
import com.example.geek.flicker.DB.FlickerDbHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by geek on 12/27/17.
 */

public class FlickerUserAdapter extends RecyclerView.Adapter<FlickerUserAdapter.UserViewHolder>{

    private Context mContext;
    Cursor userCursor;

    public FlickerUserAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_user_detail_activity,parent,false);
        FlickerUserAdapter.UserViewHolder userViewHolder= new FlickerUserAdapter.UserViewHolder(view);
        return userViewHolder;

    }

    @Override
    public void onBindViewHolder(FlickerUserAdapter.UserViewHolder holder, int position) {
            userCursor.moveToPosition(position);


            Picasso.with(mContext).load(userCursor.getString(4))
                .error(R.drawable.ic_error_black_24dp)
                .placeholder(R.drawable.place_holder_icon)
                .into(holder.userImage);
//            holder.location.setText(userCursor.getString(5));


    }

    public void swapCursor(Cursor newCursor){

        userCursor = newCursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (userCursor == null) ? 0 : userCursor.getCount();
    }



    class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;

        public UserViewHolder(View itemView) {
            super(itemView);

            this.userImage=itemView.findViewById(R.id.thumbnail);

        }



    }
}
