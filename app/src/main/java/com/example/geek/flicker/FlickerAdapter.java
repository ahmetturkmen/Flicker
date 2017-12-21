package com.example.geek.flicker;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by geek on 12/22/17.
 */

public class FlickerAdapter extends RecyclerView.Adapter<FlickerAdapter.ViewHolder> {

    private String[] mDataset;

    public FlickerAdapter(String  [] mDataset) {
        this.mDataset=mDataset;
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
        return 0;
    }

    @Override
    public void onBindViewHolder(FlickerAdapter.ViewHolder holder, int position) {

    }
    class ViewHolder extends  RecyclerView.ViewHolder{

        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);


        }

    }
}

