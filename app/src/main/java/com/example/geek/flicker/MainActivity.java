package com.example.geek.flicker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import  com.example.geek.flicker.R;

public class MainActivity extends AppCompatActivity {




    private RecyclerView mRecyclerView;
    private FlickerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<PhotoData> photoData ;
    private DividerItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
//        photoData= new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            photoData.add(new PhotoData("Andorid Title","","","","","",R.drawable.place_holder_icon));
//
//        }
       // dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), LinearLayout.HORIZONTAL);

        // Animation added.i


       // LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(),R.anim.layout_animation_fall_down );
        //mRecyclerView.setLayoutAnimation(animation);


     //   mRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new FlickerAdapter(this,photoData);
        mRecyclerView.setAdapter(mAdapter);

        String FLICKR_API_KEY="90d4261f0b2d121c44e13aa5e3e80947";
        String FLICKR_SECRET_KEY="31c8a94a5c0c757b";



        FetchImageJsonData fetchImageJsonData = new FetchImageJsonData(mAdapter);

        fetchImageJsonData.execute("https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tagmode=any");





    }



    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
