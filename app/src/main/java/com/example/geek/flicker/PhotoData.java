package com.example.geek.flicker;

import java.util.List;

/**
 * Created by geek on 22.12.2017.
 */

public class PhotoData {

    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLink;
    private String mTags;
    private String mImageURL;
    private String mPhotoTakenDate;
    private String mPhotoDescription;
    private String mPhotoPublishedDate;
    private String mPhotoID;
    public PhotoData() {
    }

    public PhotoData(String title, String author, String authorId, String link, String tags, String image) {
        mTitle = title;
        mAuthor = author;
        mAuthorId = authorId;
        mLink = link;
        mTags = tags;
        mImageURL = image;
    }

//
//    public PhotoData(String mAuthorProfilePictureURL, String mAuthorRealName, String mAuthorUserName, int numberOfPhotosOfTheUser,String profileImageURL) {
//        this.mAuthorProfilePictureURL = mAuthorProfilePictureURL;
//        this.mAuthorRealName = mAuthorRealName;
//        this.mAuthorUserName = mAuthorUserName;
//        this.numberOfPhotosOfTheUser = numberOfPhotosOfTheUser;
//        this.profileImageURL =profileImageURL;
//    }


    public String getmPhotoID() {
        return mPhotoID;
    }

    public void setmPhotoID(String mPhotoID) {
        this.mPhotoID = mPhotoID;
    }

    public String getmPhotoPublishedDate() {
        return mPhotoPublishedDate;
    }

    public void setmPhotoPublishedDate(String mPhotoPublishedDate) {
        this.mPhotoPublishedDate = mPhotoPublishedDate;
    }

    public String getmPhotoDescription() {
        return mPhotoDescription;
    }

    public void setmPhotoDescription(String mPhotoDescription) {
        this.mPhotoDescription = mPhotoDescription;
    }

    public String getmPhotoTakenDate() {
        return mPhotoTakenDate;
    }

    public void setmPhotoTakenDate(String mPhotoTakenDate) {
        this.mPhotoTakenDate = mPhotoTakenDate;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public void setmAuthorId(String mAuthorId) {
        this.mAuthorId = mAuthorId;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public void setmTags(String mTags) {
        this.mTags = mTags;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

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

    String getImageUrl() {
        return mImageURL;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImageURL='" + mImageURL + '\'' +
                '}';
    }





}
