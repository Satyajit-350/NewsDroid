package com.satyajit.newz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //api key - 703e31b49812416aa806572236d54e4b
    //api(for all) -  https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=703e31b49812416aa806572236d54e4b

    //api(for categories - https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=703e31b49812416aa806572236d54e4b

    //api(according to search) - https://newsapi.org/v2/everything?q=india&apiKey=703e31b49812416aa806572236d54e4b

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView newsRecyclerView;
    private ProgressBar loadingIndicator;
    private ImageView imageView;
    private SearchView searchView;

    private NewsAdapter newsAdapter;
    private ArrayList<articles> newsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigation drawer-------------------------------------------------------------------------------
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //find the menu items in navigation drawer to move to different activities-----------------------------------
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (menuItem.getItemId()){
                    case R.id.home_id:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.category_id:
                        Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.bookmark_id:
                        Toast.makeText(MainActivity.this,"Bookmarks",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout_id:
                        Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.share_id:
                        Toast.makeText(MainActivity.this,"Share",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        //-------------------------------------------------------------------------------------------------


        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        newsRecyclerView = findViewById(R.id.news_recycler_view);
        loadingIndicator = findViewById(R.id.loading_indicator);
        imageView = findViewById(R.id.empty_image);
        searchView = findViewById(R.id.search_news);

        newsArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsArrayList,this);


        newsRecyclerView.setAdapter(newsAdapter);


        getNews();
        newsAdapter.notifyDataSetChanged();

        //swipe to refresh---------------------------------------------------------------------------------
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                imageView.setVisibility(View.GONE);
                getNews();
                newsAdapter.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //------------------------------------------------------------------------------------------------------


        //searchView--------------------------------------------------------------------------------------------
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNewsBySearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //----------------------------------------------------------------------------------------------------------


    }

    //getting data for our news through retrofit
    private void getNews(){
        loadingIndicator.setVisibility(View.VISIBLE);
        newsArrayList.clear();

        String url = "https://newsapi.org/v2/everything?q=Apple&from=2022-01-29&sortBy=popularity&apiKey=703e31b49812416aa806572236d54e4b";
        String BASE_URL = "https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<NewsModel> call;

        call = retrofitApi.getAllNews(url);

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadingIndicator.setVisibility(View.GONE);

                ArrayList<articles>articles = newsModel.getArticles();
                for(int i = 0;i<articles.size();i++){
                    newsArrayList.add(new articles(articles.get(i).getTitle(),
                            articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent(),
                            articles.get(i).getAuthor()));
                }
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                imageView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //getting data when search is done
    private void getNewsBySearch(String news){
        imageView.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);
        newsArrayList.clear();

        String url = "https://newsapi.org/v2/everything?q="+news+"&apiKey=703e31b49812416aa806572236d54e4b";
        String BASE_URL = "https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<NewsModel> call1;
        call1 = retrofitApi.getNewsBySearch(url);

        call1.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadingIndicator.setVisibility(View.GONE);

                ArrayList<articles>articles = newsModel.getArticles();
                for(int i = 0;i<articles.size();i++){
                    newsArrayList.add(new articles(articles.get(i).getTitle(),
                            articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent(),
                            articles.get(i).getAuthor()));
                }
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

}