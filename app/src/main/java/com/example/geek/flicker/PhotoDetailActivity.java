package com.example.geek.flicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {

    private TextView photoTitle;
    private TextView photoAuthor;
    private TextView photoTags;
    private ImageView photoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        photoAuthor=findViewById(R.id.photo_author);
        photoTags=findViewById(R.id.photo_tags);
        photoTitle=findViewById(R.id.photo_title);
        photoImageView=findViewById(R.id.photo_image);

        photoAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                startActivity(new Intent(PhotoDetailActivity.this,UserDetailActivity.class));
            }
        });


        Intent receievedIntent = getIntent();

        if(receievedIntent.hasExtra("title")){
                photoTitle.setText("Title: "+receievedIntent.getStringExtra("title"));
        }
        if(receievedIntent.hasExtra("tags"))
            photoTags.setText("Tags : "+ receievedIntent.getStringExtra("tags"));
        if(receievedIntent.hasExtra("photoLink"))
            Picasso.with(this).load(receievedIntent.getStringExtra("photoLink"))
                    .error(R.drawable.place_holder_icon)
                    .placeholder(R.drawable.place_holder_icon)
                    .into(photoImageView);
        if(receievedIntent.hasExtra("authorID"))
            photoAuthor.setText("Author ID : "+ receievedIntent.getStringExtra("authorID"));
    }
}
