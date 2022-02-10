package com.satyajit.newz.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.satyajit.newz.R;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookViewHolder> {

    private List<BookMarks> bookmarks = new ArrayList<>();


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item2,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookMarks currBookmark = bookmarks.get(position);
        holder.newsTitle.setText(currBookmark.getTitle());
        holder.descriptionTitle.setText(currBookmark.getDescription());

    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    public void setNews(List<BookMarks> bookmarks){
        this.bookmarks = bookmarks;
        notifyDataSetChanged();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder{

        private TextView newsTitle;
        private TextView descriptionTitle;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle = itemView.findViewById(R.id.db_text_view_title);
            descriptionTitle = itemView.findViewById(R.id.db_text_view_description);

        }
    }
}
