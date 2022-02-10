package com.satyajit.newz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<CategoryModel> categoryArrayList;
    private CategoryClickListener categoryClickListener;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryArrayList, CategoryClickListener categoryClickListener) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_items,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModel categoryModel = categoryArrayList.get(position);
        holder.categoryTitle.setText(categoryModel.getCategories());
        Picasso.get().load(categoryModel.getCategoriesImageUrl()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickListener.onclickCategory(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView categoryTitle;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image_category);
            categoryTitle = itemView.findViewById(R.id.text_view_categories_names);
        }
    }

    //handling onclick on category items
    public interface CategoryClickListener{
        void onclickCategory(int pos);
    }
}
