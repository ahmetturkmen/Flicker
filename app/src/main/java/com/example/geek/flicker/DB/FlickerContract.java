package com.example.geek.flicker.DB;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by geek on 12/25/17.
 */

public class FlickerContract {

    public static final String CONTENT_AUTHORITY = "com.example.geek.flicker";
    public static final Uri BASE_CONTENT_URI  = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PHOTO      = "photos";
    public static final String PATH_USER     = "users";

    ///////////////////////////////////////////////////////////////////////////////
    //
    // PhotoEntry (Inner class) Class that defines the contents of the photos table
    //
    ///////////////////////////////////////////////////////////////////////////////

    public static final class PhotoEntry implements BaseColumns {

        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PHOTO)
                .build();
        public static final String CONTENT_TYPE      = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PHOTO;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PHOTO;

        public static final String TABLE_NAME       = "photos";
        public static final String COLUMN_PHOTO_ID = "photoID";
        public static final String COLOUMN_PHOTO_AUTHOR="author";
        public static final String COLUMN_IMAGE_URL= "photoURL";
        public static final String COLOUMN_USER_ID = "userID";
        public static final String COLOUMN_TAKEN_DATE= "photoTakenDate";
        public static final String COLOUMN_PUBLISH_DATE="photoPublishedDate";
        public static final String COLOUMN_TAG = "photoTag";
        public static final String COLOUMN_PHOTO_TITLE ="title";
        public static final String COLUMN_PHOTO_DESCRIPTION= "photoDesc";
        public static final String SEARCH_SETTTING = "search_settting";



        public static Uri buildPhotoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // UserEntry (Inner class) Class that defines the contents of the users table
    //
    ///////////////////////////////////////////////////////////////////////////////



    public static final class UserEntry implements BaseColumns {

        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USER)
                .build();
        public static final String CONTENT_TYPE      = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME               = "users";

        public static final String COLUMN_ID           = "ID";
        public static final String COLUMN_USERNAME      = "username";
        public static final String COLUMN_REALNAME      = "realname";
        public static final String COLUMN_NUMBER_OF_PHOTOS     = "numberOfPhotos";


        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }



    }





}
