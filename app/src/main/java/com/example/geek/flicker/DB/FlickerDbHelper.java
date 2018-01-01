package com.example.geek.flicker.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.geek.flicker.FetchUserJsonData;
import com.example.geek.flicker.FlickerUserAdapter;

import static android.content.ContentValues.TAG;

/**
 * Created by geek on 12/25/17.
 */

public class FlickerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =2;
    private static final String DATABASE_NAME = "flicker.db";

    public FlickerDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE "+ FlickerContract.UserEntry.TABLE_NAME +" ( "+
                FlickerContract.UserEntry.COLUMN_ID + " INTEGER PRIMARY KEY,"+
                FlickerContract.UserEntry.COLUMN_USERNAME + " TEXT," +
                FlickerContract.UserEntry.COLUMN_REALNAME + " TEXT,"+
                FlickerContract.UserEntry.COLOUMN_LOCATION+ " TEXT,"+
                FlickerContract.UserEntry.COLUMN_NUMBER_OF_PHOTOS+ " INTEGER,"+
                FlickerContract.UserEntry.COLOUMN_PROFILE_IMAGE_URL + " TEXT"
                +" );";

        final String SQL_CREATE_PHOTOS_TABLE=" CREATE TABLE "+ FlickerContract.PhotoEntry.TABLE_NAME + " ( "+
                FlickerContract.PhotoEntry.COLUMN_PHOTO_ID + " TEXT PRIMARY KEY,"+
                FlickerContract.PhotoEntry.COLOUMN_PHOTO_TITLE+ " TEXT,"+
                FlickerContract.PhotoEntry.COLOUMN_TAKEN_DATE+ " TEXT,"+
                FlickerContract.PhotoEntry.COLOUMN_PUBLISH_DATE+ " TEXT,"+
                FlickerContract.PhotoEntry.COLUMN_IMAGE_URL+ " TEXT,"+
                FlickerContract.PhotoEntry.COLOUMN_TAG+ " TEXT,"+
                FlickerContract.PhotoEntry.COLOUMN_PHOTO_AUTHOR+ " TEXT,"+
                FlickerContract.PhotoEntry.COLUMN_PHOTO_DESCRIPTION+ " TEXT,"+
                FlickerContract.PhotoEntry.COLOUMN_USER_ID+ " TEXT,"+
                " FOREIGN KEY ("+FlickerContract.PhotoEntry.COLOUMN_USER_ID+") REFERENCES "+
                FlickerContract.UserEntry.COLUMN_ID+"("+ FlickerContract.UserEntry.COLUMN_ID+"));";

        Log.v("SQL STATEMENT: ",SQL_CREATE_PHOTOS_TABLE);
        Log.v("SQL STATEMENT: ",SQL_CREATE_USERS_TABLE);

        db.execSQL(SQL_CREATE_PHOTOS_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FlickerContract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FlickerContract.PhotoEntry.TABLE_NAME);
        onCreate(db);

    }
}
