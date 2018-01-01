package com.example.geek.flicker;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geek.flicker.DB.FlickerContract;
import com.example.geek.flicker.DB.FlickerDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private RecyclerView mRecyclerView;
    private TextView userName,realName,numberOfPhotos,location;
        private ImageView userImage;
    private FlickerDbHelper flickerDbHelper;
    Cursor cursor;

    public static final String[] FLICKER_USER_PROJECTIONS = {
            FlickerContract.UserEntry.COLUMN_ID,
            FlickerContract.UserEntry.COLUMN_USERNAME,
            FlickerContract.UserEntry.COLUMN_REALNAME,
            FlickerContract.UserEntry.COLUMN_NUMBER_OF_PHOTOS,
            FlickerContract.UserEntry.COLOUMN_PROFILE_IMAGE_URL,
            FlickerContract.UserEntry.COLOUMN_LOCATION
    };
    private int LOADER_ID = 1;

    private FlickerUserAdapter flickerUserAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Intent receivedIntent = getIntent();
        userName = findViewById(R.id.username);
        realName = findViewById(R.id.realname);
        numberOfPhotos = findViewById(R.id.numberofPhotos);
        location = findViewById(R.id.location);
        userImage = findViewById(R.id.imageView);

        mRecyclerView = findViewById(R.id.detailActivityRecyclerView);
        flickerDbHelper = new FlickerDbHelper(this);
        Log.v("incoimngUSERID", receivedIntent.getStringExtra("user_id") + " ");
        String user_id = receivedIntent.getStringExtra("user_id");

        getLoaderManager().initLoader(LOADER_ID, null, this);
        fetchUserData(receivedIntent.getStringExtra("user_id"));

        mRecyclerView.setHasFixedSize(true);



        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);


        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        flickerUserAdapter = new FlickerUserAdapter(this);
        mRecyclerView.setAdapter(flickerUserAdapter);


//        String query = "SELECT * FROM "+ FlickerContract.PhotoEntry.TABLE_NAME+ " INNER JOIN "+
//                FlickerContract.UserEntry.TABLE_NAME+ " ON "+ FlickerContract.UserEntry.COLUMN_ID+ "="+
//                FlickerContract.PhotoEntry.COLOUMN_USER_ID + " WHERE "+ FlickerContract.PhotoEntry.COLOUMN_USER_ID +"=?";

        String query = "SELECT * FROM " + FlickerContract.UserEntry.TABLE_NAME + " WHERE " + FlickerContract.UserEntry.COLUMN_ID + "=" + "'" + user_id + "'";

        //   cursor=sqLiteDatabase.query(FlickerContract.UserEntry.TABLE_NAME,FLICKER_USER_PROJECTIONS,null,null,null,null,null);


        String[] arrayOfUsers = getUserInfoFromDB(user_id);

        userName.setText(arrayOfUsers[1]);
        realName.setText(arrayOfUsers[2]);
        Picasso.with(this.getApplicationContext()).load(arrayOfUsers[4])
                .error(R.drawable.ic_error_black_24dp)
                .placeholder(R.drawable.place_holder_icon)
                .into(userImage);

        numberOfPhotos.setText(arrayOfUsers[3]);
        location.setText(arrayOfUsers[5]);

    }

    private  void fetchUserData(String USERID) {

        final String API_KEY = "629a817d24e555d14f7bf242f4deadfc";
        final String FORMAT = "json";
        final String METHOD = "flickr.people.getInfo";
        final String NO_JSON_CALL_BACK = "1";



        final String FLIKCR_BASE_URL = "https://api.flickr.com/services/rest/?";
        final String METHOD_PARAM = "method";
        final String APPKEY_PARAM = "api_key";
        final String USER_ID_PARAM = "user_id";
        final String FORMAT_PARAM = "format";
        final String FORMAT_PARAM_HANDLER = "nojsoncallback";

        Uri builtUri = Uri.parse(FLIKCR_BASE_URL).buildUpon()
                .appendQueryParameter(METHOD_PARAM, METHOD)
                .appendQueryParameter(APPKEY_PARAM, API_KEY)
                .appendQueryParameter(USER_ID_PARAM, USERID)
                .appendQueryParameter(FORMAT_PARAM, FORMAT)
                .appendQueryParameter(FORMAT_PARAM_HANDLER, NO_JSON_CALL_BACK)
                .build();
        Log.v("builtURI"," "+builtUri.toString());

        FetchUserJsonData fetchUserJsonData = new FetchUserJsonData(this.getApplicationContext());

        fetchUserJsonData.execute(builtUri.toString());


    }


    public String[] getUserInfoFromDB(String user_id) {


        String query = "SELECT * FROM "+ FlickerContract.UserEntry.TABLE_NAME + " WHERE " + FlickerContract.UserEntry.COLUMN_ID+ "="+"'"+user_id+"'";
        SQLiteDatabase db  = flickerDbHelper.getReadableDatabase();
        Cursor cursor = db.query(FlickerContract.UserEntry.TABLE_NAME,FLICKER_USER_PROJECTIONS,null,null,null,null,null,null);
        String data[]     = new String[100];

        if (cursor.moveToFirst()) {
            do {
               data[0]=cursor.getString(1);
               data[1]=cursor.getString(2);
               data[2]=cursor.getString(3);
               data[4]=cursor.getString(4);
               data[5]=cursor.getString(5);
                // get the data into array, or class variable
            } while (cursor.moveToNext());
        }

        return data;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this.getApplicationContext(), FlickerContract.UserEntry.CONTENT_URI, FLICKER_USER_PROJECTIONS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        flickerUserAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        flickerUserAdapter.swapCursor(null);
    }
}
