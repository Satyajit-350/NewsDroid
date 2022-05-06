package com.satyajit.newz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.r0adkll.slidr.Slidr;
import com.satyajit.newz.database.BookMarks;
import com.satyajit.newz.database.BookmarkViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickListener, NewsAdapter.BookmarkClickListener {

    private RecyclerView recyclerView1, recyclerView2;
    private ShimmerFrameLayout shimmerFrameLayout;
    private NewsAdapter newsAdapter1;
    private CategoryAdapter categoryAdapter1;
    private ArrayList<articles> newsArrayList1;
    private ArrayList<CategoryModel> categoryArrayList1;
    private TextView categorySelectTextView;

    private BookmarkViewModel bookmarkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        int primary = getResources().getColor(R.color.black);
        int secondary = getResources().getColor(R.color.black);
        Slidr.attach(this,primary,secondary);


        recyclerView1 = findViewById(R.id.categories_recycler_view1);
        recyclerView2 = findViewById(R.id.news_recycler_view1);
        shimmerFrameLayout = findViewById(R.id.shimmer1);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        categorySelectTextView = findViewById(R.id.category_text_view);

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);

        categoryArrayList1 = new ArrayList<>();
        newsArrayList1 = new ArrayList<>();
        newsAdapter1 = new NewsAdapter(newsArrayList1, this, this::onclickBookmark);
        categoryAdapter1 = new CategoryAdapter(this, categoryArrayList1, this::onclickCategory);

        recyclerView1.setLayoutManager(new GridLayoutManager(this,2));

        recyclerView2.setAdapter(newsAdapter1);
        recyclerView1.setAdapter(categoryAdapter1);

        getCategories();

        categorySelectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView1.setVisibility(View.VISIBLE);

            }
        });

    }

    //getting data for our categories
    private void getCategories() {
        categoryArrayList1.add(new CategoryModel("All", "https://images.unsplash.com/photo-1588681664899-f142ff2dc9b1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8bmV3c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryArrayList1.add(new CategoryModel("Technology", "https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8dGVjaHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryArrayList1.add(new CategoryModel("Business", "https://images.unsplash.com/photo-1578574577315-3fbeb0cecdc2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8YnVzaW5lc3N8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryArrayList1.add(new CategoryModel("Health", "https://images.unsplash.com/photo-1505751172876-fa1923c5c528?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8aGVhbHRofGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryArrayList1.add(new CategoryModel("Entertainment", "https://media.istockphoto.com/photos/popcorn-and-clapperboard-picture-id1191001701?b=1&k=20&m=1191001701&s=170667a&w=0&h=uVqDpnXNtnfbhB-F4sWac_t3oL_YSrDuHeCKdaJGS3U="));
        categoryArrayList1.add(new CategoryModel("Sports", "https://images.unsplash.com/photo-1594470117722-de4b9a02ebed?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8Y3JpY2tldHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryAdapter1.notifyDataSetChanged();
    }

    //getting data for our news through retrofit
    private void getNews(String categories) {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        newsArrayList1.clear();

        String category = "https://newsapi.org/v2/top-headlines?country=in&category=" + categories + "&apiKey=703e31b49812416aa806572236d54e4b";
        String url = "https://newsapi.org/v2/everything?q=india&apiKey=703e31b49812416aa806572236d54e4b";
        String BASE_URL = "https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<NewsModel> call;

        if (categories.equals("All")) {
            call = retrofitApi.getAllNews(url);
        } else {
            call = retrofitApi.getNewsByCategory(category);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                shimmerFrameLayout.setVisibility(View.GONE);

                ArrayList<articles> articles = newsModel.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    newsArrayList1.add(new articles(articles.get(i).getTitle(),
                            articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent(),
                            articles.get(i).getAuthor()));
                }
                newsAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onclickCategory(int pos) {
        recyclerView1.setVisibility(View.GONE);
        recyclerView2.setVisibility(View.VISIBLE);
        String category = categoryArrayList1.get(pos).getCategories();
        getNews(category);
    }

    @Override
    public void onclickBookmark(int pos) {

        String bookmarkTitle = newsArrayList1.get(pos).getTitle();
        String bookmarkDesc = newsArrayList1.get(pos).getDescription();

        BookMarks bookmarks = new BookMarks(bookmarkTitle,bookmarkDesc);
        bookmarkViewModel.insert(bookmarks);
        Toast.makeText(this, "Bookmark Saved", Toast.LENGTH_SHORT).show();

    }
}