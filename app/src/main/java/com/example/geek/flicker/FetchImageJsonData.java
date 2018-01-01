package com.example.geek.flicker;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.geek.flicker.DB.FlickerContract;
import com.example.geek.flicker.DB.FlickerContract.PhotoEntry;
import com.example.geek.flicker.DB.FlickerDbHelper;

/**
 * Created by geek on 22.12.2017.
 */

public class FetchImageJsonData extends AsyncTask<String, Void, Void> {


    ContentResolver contentResolver;
    Context context;
    FlickerPhotoAdapter flickerPhotoAdapter;
    private List<PhotoData> mPhotoList = null;
    FlickerDbHelper flickerDbHelper;

    public FetchImageJsonData(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        flickerDbHelper=new FlickerDbHelper(context);
    }

    @Override
    protected Void doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String imageJsonString = null;
        Log.v("doInBackground", strings[0]);


        try {
            URL url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() != 0) {
                    imageJsonString = buffer.toString();
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("MainActivity", "Error closing stream", e);
                }
            }
        }

        try {
            getDataFromJson(imageJsonString);
        } catch (JSONException e) {
            Log.e("FetchImageTask", e.getMessage(), e);
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;

    }

    private void getDataFromJson(String imageJsonString) throws JSONException {
//

        Log.v("getDataFromJson", imageJsonString + " ");
        JSONObject jsonData = new JSONObject(imageJsonString);
        JSONArray itemsArray = jsonData.getJSONArray("items");
        Pattern pattern = Pattern.compile("([^\\/]+$)");
        Matcher matcher;

        for (int i = 0; i < itemsArray.length(); i++) {
            PhotoData photoData = new PhotoData();

            JSONObject jsonPhoto = itemsArray.getJSONObject(i);
            JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
            String photoUrl = jsonMedia.getString("m");
//
//          String link = photoUrl.replaceFirst("_m.", "_b.");
            String photoID = "";
            matcher = pattern.matcher(photoUrl);
            if (matcher.find())
                photoID = matcher.group();


            photoData.setmTitle(jsonPhoto.getString("title"));
            photoData.setmAuthor(jsonPhoto.getString("author"));
            photoData.setmAuthorId(jsonPhoto.getString("author_id"));
            photoData.setmPhotoDescription(jsonPhoto.getString("description"));
            photoData.setmPhotoTakenDate(jsonPhoto.getString("date_taken"));
            photoData.setmPhotoID(photoID.split("_m")[0]);
            photoData.setmPhotoPublishedDate(jsonPhoto.getString("published"));
            photoData.setmTags(jsonPhoto.getString("tags"));
            photoData.setmImageURL(photoUrl);

            addPhotoInfo(photoData);


        }

    }


    public void addPhotoInfo(PhotoData photoData) {
        ContentValues photoContentValues = new ContentValues();

        photoContentValues.put(PhotoEntry.COLOUMN_TAG, photoData.getTags());
        photoContentValues.put(PhotoEntry.COLUMN_IMAGE_URL, photoData.getImageUrl());
        photoContentValues.put(PhotoEntry.COLUMN_PHOTO_ID, photoData.getmPhotoID());
        photoContentValues.put(PhotoEntry.COLOUMN_USER_ID, photoData.getAuthorId());
        photoContentValues.put(PhotoEntry.COLOUMN_PHOTO_AUTHOR, photoData.getAuthor());
        photoContentValues.put(PhotoEntry.COLOUMN_PHOTO_TITLE, photoData.getTitle());
        photoContentValues.put(PhotoEntry.COLOUMN_TAKEN_DATE, photoData.getmPhotoTakenDate());
        photoContentValues.put(PhotoEntry.COLOUMN_PUBLISH_DATE, photoData.getmPhotoPublishedDate());
        photoContentValues.put(PhotoEntry.COLUMN_PHOTO_DESCRIPTION, photoData.getmPhotoDescription());

        if(!isExist(photoData.getmPhotoID()))
            contentResolver.insert(PhotoEntry.CONTENT_URI, photoContentValues);

    }




    public boolean isExist(String PHOTOID){
        SQLiteDatabase db = flickerDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + FlickerContract.PhotoEntry.TABLE_NAME + " WHERE photoID = '" + PHOTOID + "'", null);
        boolean exist = (cursor.getCount() > 0);
        cursor.close();

        // I did not close Database connection intentionally because I want to show table contents in log.
        // By using getTableAsString method.

        // db.close();
        return exist;
    }
}