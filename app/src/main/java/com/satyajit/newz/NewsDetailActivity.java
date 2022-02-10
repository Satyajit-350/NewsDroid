package com.satyajit.newz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    private ImageView newsImageView;
    private TextView heading_text,desc_text,content_text;
    private Button detailedButton;

    String title;
    String description;
    String content;
    String imageUrl;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        int primary = getResources().getColor(R.color.black);
        int secondary = getResources().getColor(R.color.black);
        Slidr.attach(this,primary,secondary);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        content = getIntent().getStringExtra("content");
        imageUrl = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        newsImageView = findViewById(R.id.detailed_image_view);
        heading_text = findViewById(R.id.detailed_heading_text);
        desc_text = findViewById(R.id.detailed_description_text_view);
        content_text = findViewById(R.id.detailed_content_text_view);
        detailedButton = findViewById(R.id.detailed_button);

        heading_text.setText(title);
        desc_text.setText(description);
        content_text.setText(content);
        Picasso.get().load(imageUrl).into(newsImageView);

        detailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }
}