package com.example.geek.flicker;

/**
 * Created by geek on 22.12.2017.
 */

public class PhotoData {

    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLink;
    private String mTags;
    private String mImage;
//    private int resourceID;

    public PhotoData(String title, String author, String authorId, String link, String tags, String image) {
        mTitle = title;
        mAuthor = author;
//        this.resourceID=resourceID;
        mAuthorId = authorId;
        mLink = link;
        mTags = tags;
        mImage = image;
    }
//
//    public int getResourceID() {
//        return resourceID;
//    }
//
//    public void setResourceID(int resourceID) {
//        this.resourceID = resourceID;
//    }

    String getTitle() {
        return mTitle;
    }

    String getAuthor() {
        return mAuthor;
    }

    String getAuthorId() {
        return mAuthorId;
    }

    String getLink() {
        return mLink;
    }

    String getTags() {
        return mTags;
    }

    String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }



}
