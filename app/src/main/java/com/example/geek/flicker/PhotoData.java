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
    private String mAuthorProfilePictureURL;
    private String mAuthorRealName;
    private String mAuthorUserName;
    private String profileImageURL;
    private int numberOfPhotosOfTheUser;

    public PhotoData(String title, String author, String authorId, String link, String tags, String image) {
        mTitle = title;
        mAuthor = author;
        mAuthorId = authorId;
        mLink = link;
        mTags = tags;
        mImage = image;
    }


    public PhotoData(String mAuthorProfilePictureURL, String mAuthorRealName, String mAuthorUserName, int numberOfPhotosOfTheUser,String profileImageURL) {
        this.mAuthorProfilePictureURL = mAuthorProfilePictureURL;
        this.mAuthorRealName = mAuthorRealName;
        this.mAuthorUserName = mAuthorUserName;
        this.numberOfPhotosOfTheUser = numberOfPhotosOfTheUser;
        this.profileImageURL =profileImageURL;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getmAuthorProfilePictureURL() {
        return mAuthorProfilePictureURL;
    }

    public String getmAuthorRealName() {
        return mAuthorRealName;
    }

    public String getmAuthorUserName() {
        return mAuthorUserName;
    }

    public int getNumberOfPhotosOfTheUser() {
        return numberOfPhotosOfTheUser;
    }

    public void setmAuthorProfilePictureURL(String mAuthorProfilePictureURL) {
        this.mAuthorProfilePictureURL = mAuthorProfilePictureURL;
    }

    public void setmAuthorRealName(String mAuthorRealName) {
        this.mAuthorRealName = mAuthorRealName;
    }

    public void setmAuthorUserName(String mAuthorUserName) {
        this.mAuthorUserName = mAuthorUserName;
    }

    public void setNumberOfPhotosOfTheUser(int numberOfPhotosOfTheUser) {
        this.numberOfPhotosOfTheUser = numberOfPhotosOfTheUser;
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
