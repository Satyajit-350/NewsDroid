package com.satyajit.newz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.satyajit.newz.database.BookmarkViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public static final String EXTRA_ID = "com.satyajit.newz.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.satyajit.newz.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.satyajit.newz.EXTRA_DESCRIPTION";

    private ArrayList<articles> articlesArrayList;

    private Context context;

    public NewsAdapter(ArrayList<articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        articles articles = articlesArrayList.get(position);
        holder.heading.setText(articles.getTitle());
        holder.subTitle.setText(articles.getDescription());
        holder.author.setText(articles.getAuthor());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsImage);

        holder.bookmarkImage.setImageResource(R.drawable.ic_bookmark);

        holder.relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,NewsDetailActivity.class);
                intent.putExtra("title",articles.getTitle());
                intent.putExtra("description",articles.getDescription());
                intent.putExtra("url",articles.getUrl());
                intent.putExtra("content",articles.getContent());
                intent.putExtra("image",articles.getUrlToImage());
                context.startActivity(intent);
            }
        });

        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = articles.getTitle();
                String url = articles.getUrl();
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "Hey!! I have some news for you.. \n"
                +"\n*"+title+"*"+"\nFollow this Link: \n"
                +url);
                context.startActivity(i);
            }
        });

        holder.bookmarkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bookmarkImage.setImageResource(R.drawable.ic_bookmark_red);
//                Intent bookmarkData = new Intent();
//                bookmarkData.putExtra(EXTRA_TITLE,articles.getTitle());
//                bookmarkData.putExtra(EXTRA_DESCRIPTION,articles.getDescription());
                Toast.makeText(view.getContext(), "bookmarked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout relativeLayout1;
        private ImageView newsImage;
        private TextView heading;
        private TextView subTitle;
        private TextView author;
        private ImageView bookmarkImage,shareImage;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout1 = itemView.findViewById(R.id.relative_card_layout);
            newsImage = itemView.findViewById(R.id.news_images);
            heading = itemView.findViewById(R.id.heading_title);
            subTitle = itemView.findViewById(R.id.subtitle_text);
            author = itemView.findViewById(R.id.author_text_view);
            bookmarkImage = itemView.findViewById(R.id.bookmark_image_view);
            shareImage = itemView.findViewById(R.id.share_image_view);
        }
    }
}
