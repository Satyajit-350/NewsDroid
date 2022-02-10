package com.satyajit.newz.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BooksMarksDao {

    @Insert
    void insert(BookMarks bookMarks);

    @Delete
    void delete(BookMarks bookMarks);

    @Query("DELETE FROM bookmark_table")
    void deleteAll();

    @Query("SELECT*FROM bookmark_table ORDER BY id ASC")
    LiveData<List<BookMarks>> getAllBookmarks();
}
