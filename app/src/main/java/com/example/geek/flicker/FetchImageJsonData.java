package com.example.geek.flicker;

import android.net.Uri;
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

import static android.content.ContentValues.TAG;

/**
 * Created by geek on 22.12.2017.
 */

public class FetchImageJsonData extends AsyncTask<String, Void,List<PhotoData>>{

    FlickerAdapter flickerAdapter;
    private List<PhotoData> mPhotoList = null;

    public FetchImageJsonData(FlickerAdapter flickerAdapter){
        this.flickerAdapter=flickerAdapter;
    }

    @Override
    protected List<PhotoData>  doInBackground(String... strings) {
        HttpURLConnection urlConnection   = null;
        BufferedReader reader          = null;
        String 		      imageJsonString = null;

        try {
            URL weatherURL = new URL(strings[0]);
            urlConnection  = (HttpURLConnection) weatherURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer     = new StringBuffer();

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
        } finally{
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
            return getImageDataFromJson(imageJsonString);
        } catch (JSONException e) {
            Log.e("FetchWeatherTask", e.getMessage(), e);
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;

    }

    private List<PhotoData> getImageDataFromJson(String imageJsonString) throws JSONException {

        mPhotoList=new ArrayList<>();

        JSONObject jsonData = new JSONObject(imageJsonString);
        JSONArray itemsArray = jsonData.getJSONArray("items");

        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject jsonPhoto = itemsArray.getJSONObject(i);
            String title = jsonPhoto.getString("title");
            String author = jsonPhoto.getString("author");
            String authorId = jsonPhoto.getString("author_id");
            String tags = jsonPhoto.getString("tags");

            JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
            String photoUrl = jsonMedia.getString("m");

            String link = photoUrl.replaceFirst("_m.", "_b.");

            PhotoData photoObject = new PhotoData(title, author, authorId, link, tags, photoUrl);
            mPhotoList.add(photoObject);

            Log.d(TAG, "onDownloadComplete " + photoObject.toString());


        }

        return  mPhotoList;
    }

//    private String createUri(String searchCriteria, String lang, boolean matchAll) {
//        Log.d(TAG, "createUri starts");
//
//        String URI = Uri.parse(mBaseURL).buildUpon()
//                .appendQueryParameter("tags", searchCriteria)
//                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
//                .appendQueryParameter("lang", lang)
//                .appendQueryParameter("format", "json")
//                .appendQueryParameter("nojsoncallback", "1")
//                .build().toString();
//        Log.v("URI: ",""+URI);
//        return URI;
//    }

    @Override
    protected void onPostExecute(List<PhotoData> photoDataList) {
        super.onPostExecute(photoDataList);
        flickerAdapter.setPhotoData(photoDataList);
    }
}