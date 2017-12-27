package com.example.geek.flicker.DB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by geek on 12/26/17.
 */

public class FlickerContentProvider extends ContentProvider {

    private static final int PHOTO= 100;
    private static final int USER = 300;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private FlickerDbHelper flickerDbHelper;

    @Override
    public boolean onCreate() {
        flickerDbHelper = new FlickerDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {


        final SQLiteDatabase db    = flickerDbHelper.getReadableDatabase();
        final int            match = sUriMatcher.match(uri);
        Cursor               retCursor;
        switch(match){
            case USER: {
                retCursor = db.query(FlickerContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            } break;

            case PHOTO: {
                retCursor = db.query(FlickerContract.PhotoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            } break;


            default:{
                throw new UnsupportedOperationException("Unknown uri:" + uri);
            }
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case PHOTO: return FlickerContract.PhotoEntry.CONTENT_TYPE;
            case USER : return FlickerContract.UserEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db    = flickerDbHelper.getWritableDatabase();
        final int            match = sUriMatcher.match(uri);
        Uri                  returnUri;


        switch(match){
            case PHOTO: {
                long _id = db.insert(FlickerContract.PhotoEntry.TABLE_NAME, null, values);

                if (_id > 0){
                    returnUri = FlickerContract.PhotoEntry.buildPhotoUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }

            } break;
            case USER:{
                long _id = db.insert(FlickerContract.UserEntry.TABLE_NAME, null, values);

                if (_id > 0){
                    returnUri = FlickerContract.UserEntry.buildUserUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
            } break;
            default:{
                throw new UnsupportedOperationException("Unknown uri:" + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
        String authority = FlickerContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority,FlickerContract.PATH_USER,USER);
        uriMatcher.addURI(authority,FlickerContract.PATH_PHOTO,PHOTO);
        return uriMatcher;
    }
}
