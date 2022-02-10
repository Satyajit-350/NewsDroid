package com.satyajit.newz.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BookMarks.class},version = 1,exportSchema = false)
public abstract class BookmarkDatabase extends RoomDatabase {

    //singleton class
    private static BookmarkDatabase INSTANCE;

    public abstract BooksMarksDao booksMarksDao();

    public static synchronized BookmarkDatabase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),BookmarkDatabase.class,"Bookmarks_db")
                    .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
