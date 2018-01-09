package com.example.geek.flicker;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.geek.flicker.DB.FlickerContract;
import com.example.geek.flicker.DB.FlickerDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by geek on 12/27/17.
 */

public class FetchUserJsonData extends AsyncTask<String, Void, Void> {

    ContentResolver contentResolver;
    Context context;
    FlickerDbHelper flickerDbHelper;

    public FetchUserJsonData(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        flickerDbHelper=new FlickerDbHelper(context);
    }

    @Override
    protected Void doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String userInfoJsonString = null;

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
                    userInfoJsonString = buffer.toString();
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
            getUserInfoFromJson(userInfoJsonString);
        } catch (JSONException e) {
            Log.e("FetchUserJsonData", e.getMessage(), e);
        }


        return null;
    }


    public  void getUserInfoFromJson(String userInfoJsonString) throws JSONException {

        String profileImageURL="", userName="", realName="", location="";
        String  icon_farm="", icon_server="", _nsid="";
        int numberOfPhotos;


        /////////////////
        //   IMPORTANT !
        //
        // Since the API call does not return ant array of JSON object (it returns JSONObject directly),
        // I re-assigned JSON Object to get new info about the user.
        ////////////////

        UserData user = new UserData();

        JSONObject jsonObject = new JSONObject(userInfoJsonString);

        Log.v("ERROR: "," "+userInfoJsonString);
        Log.v("jsonObject"," "+jsonObject.toString());

        JSONObject personJsonObject = jsonObject.getJSONObject("person");


        _nsid = personJsonObject.getString("nsid");
        icon_farm = personJsonObject.getString("iconfarm");
        icon_server = personJsonObject.getString("iconserver");


        userName = personJsonObject.getJSONObject("username").getString("_content");

        if(personJsonObject.has("realname"))
          realName = personJsonObject.getJSONObject("realname").getString("_content");

        if(personJsonObject.has("location"))
            location = personJsonObject.getJSONObject("location").getString("_content");


        numberOfPhotos = personJsonObject.getJSONObject("photos").getJSONObject("count").getInt("_content");


        profileImageURL = "http://farm" + icon_farm + ".staticflickr.com/" + icon_server + "/buddyicons/" + _nsid + ".jpg";

        user.setUserName(userName);
        user.setRealName(realName);
        user.setLocation(location);
        user.setNumberOfPhotos(numberOfPhotos);
        user.setUserImageURL(profileImageURL);

        addUserInfo(user);

    }

    public  void addUserInfo(UserData userData) {

        // check if the use exist in db, if so do not insert it again



        ContentValues userContentValues = new ContentValues();
        userContentValues.put(FlickerContract.UserEntry.COLUMN_ID,userData.getId());
        userContentValues.put(FlickerContract.UserEntry.COLUMN_NUMBER_OF_PHOTOS,userData.getNumberOfPhotos());
        userContentValues.put(FlickerContract.UserEntry.COLUMN_USERNAME,userData.getUserName());
        userContentValues.put(FlickerContract.UserEntry.COLUMN_REALNAME,userData.getRealName());
        userContentValues.put(FlickerContract.UserEntry.COLOUMN_PROFILE_IMAGE_URL,userData.getUserImageURL());
        userContentValues.put(FlickerContract.UserEntry.COLOUMN_LOCATION,userData.getLocation());
        if(isExist(userData.getId()))
            Log.v("userAlreadyINDB"," "+userData.getId());
        else
        contentResolver.insert(FlickerContract.UserEntry.CONTENT_URI,userContentValues);

    }



    public boolean isExist(String user_id){
        SQLiteDatabase db = flickerDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + FlickerContract.UserEntry.TABLE_NAME + " WHERE ID ='" + user_id+"'", null);
        boolean exist = (cursor.getCount() > 0);
        cursor.close();

        // I did not close Database connection intentionally because I want to show table contents in log.
        // By using getTableAsString method.

        // db.close();
        return exist;
    }


}
