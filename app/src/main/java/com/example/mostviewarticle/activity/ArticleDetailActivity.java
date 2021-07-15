package com.example.mostviewarticle.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mostviewarticle.R;

public class ArticleDetailActivity extends AppCompatActivity {

    ImageView back,imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);


        back = findViewById(R.id.back);
        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.detail);

        if(getIntent().getStringExtra("image").isEmpty()){
            Glide.with(this).load(R.drawable.photo).into(imageView);
        }else{
            Glide.with(this).load(getIntent().getStringExtra("image")).into(imageView);
        }

        textView.setText(getIntent().getStringExtra("detail"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}