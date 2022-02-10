package com.satyajit.newz;

public class CategoryModel {

    private String categories;
    private String categoriesImageUrl;

    public CategoryModel(String categories, String categoriesImageUrl) {
        this.categories = categories;
        this.categoriesImageUrl = categoriesImageUrl;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCategoriesImageUrl() {
        return categoriesImageUrl;
    }

    public void setCategoriesImageUrl(String categoriesImageUrl) {
        this.categoriesImageUrl = categoriesImageUrl;
    }
}
