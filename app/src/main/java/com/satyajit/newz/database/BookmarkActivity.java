package com.satyajit.newz.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.satyajit.newz.MainActivity;
import com.satyajit.newz.NewsAdapter;
import com.satyajit.newz.R;

import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    private BookmarkViewModel bookmarkViewModel;

    private RecyclerView recyclerView;

    private Toolbar bookmarkToolbar;

    BookmarkAdapter bookmarkAdapter;

    public static final int ADD_NEWS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        int primary = getResources().getColor(R.color.black);
        int secondary = getResources().getColor(R.color.black);
        Slidr.attach(this,primary,secondary);

        bookmarkToolbar = (Toolbar) findViewById(R.id.bookmark_toolbar);

        setSupportActionBar(bookmarkToolbar);

        recyclerView = findViewById(R.id.database_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        bookmarkAdapter = new BookmarkAdapter();
        recyclerView.setAdapter(bookmarkAdapter);

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        bookmarkViewModel.getAllBookmarks().observe(this, new Observer<List<BookMarks>>() {
            @Override
            public void onChanged(List<BookMarks> bookMarks) {
                bookmarkAdapter.setNews(bookMarks);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NEWS_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(MainActivity.EXTRA_ID);
            String description = data.getStringExtra(MainActivity.EXTRA_DESCRIPTION);

            BookMarks bookmarks = new BookMarks(title,description);
            bookmarkViewModel.insert(bookmarks);
            Toast.makeText(this, "Bookmark Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookmark_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_all_bookmarks:
                bookmarkViewModel.deleteAll();
                Toast.makeText(this, "All Bookmarks deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}