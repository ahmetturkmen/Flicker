package com.example.geek.flicker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {

    private TextView photoTitle;
    private TextView photoAuthor,photoPublishedDate;
    private TextView photoTags,photoTakenDate;
    private ImageView photoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        Intent receievedIntent = getIntent();

        photoAuthor=findViewById(R.id.photo_author);
        photoTags=findViewById(R.id.photo_tags);
        photoTitle=findViewById(R.id.photo_title);
        photoImageView=findViewById(R.id.photo_image);
        photoPublishedDate=findViewById(R.id.publishDate);
        photoTakenDate=findViewById(R.id.photoTakenDate);

        final String userID = receievedIntent.getStringExtra("authorID");

        photoAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent userDetailActivity = new Intent(PhotoDetailActivity.this,UserDetailActivity.class);
                userDetailActivity.putExtra("user_id",userID);
                startActivity(userDetailActivity);
            }
        });




        if(receievedIntent.hasExtra("title")){
                photoTitle.setTextColor(Color.RED);
                photoTitle.setText("Title: "+receievedIntent.getStringExtra("title"));
        }
        if(receievedIntent.hasExtra("tags"))
            photoTags.setText("Tags : "+ receievedIntent.getStringExtra("tags"));
        if(receievedIntent.hasExtra("photoLink"))
            Picasso.with(this).load(receievedIntent.getStringExtra("photoLink"))
                    .error(R.drawable.place_holder_icon)
                    .placeholder(R.drawable.place_holder_icon)
                    .into(photoImageView);
        if(receievedIntent.hasExtra("author")) {
            photoAuthor.setTextColor(Color.BLUE);
            photoAuthor.setText("Author  : " + receievedIntent.getStringExtra("author"));
        }
        if(receievedIntent.hasExtra("publishedDate"))
            photoPublishedDate.setText("Published Date : "+receievedIntent.getStringExtra("publishedDate"));
        if(receievedIntent.hasExtra("coloumnTakenDate"))
            photoTakenDate.setText("Photo Taken date : "+receievedIntent.getStringExtra("coloumnTakenDate"));

    }
}
