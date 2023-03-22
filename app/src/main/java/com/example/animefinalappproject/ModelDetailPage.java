package com.example.animefinalappproject;

// Illya Chebotaryov
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ModelDetailPage extends AppCompatActivity {

    //This simple second activity just has a bit more info more precisely the title and synopsis
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail_page);

        TextView tvTitleDetail = findViewById(R.id.tv_title_detail);
        ImageView ivPosterDetail = findViewById(R.id.iv_poster_detail);
        TextView tvSynopsisDetail = findViewById(R.id.tv_synopsis_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String synopsis = intent.getStringExtra("synopsis");
        String imageUrl = intent.getStringExtra("image_url");

        tvTitleDetail.setText(title);
        tvSynopsisDetail.setText(synopsis);
        Glide.with(this).load(imageUrl).into(ivPosterDetail); //Using Glide in order to set the image from the image url
    }
}
