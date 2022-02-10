package com.satyajit.newz.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmark_table")
public class BookMarks {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;


    public BookMarks(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
