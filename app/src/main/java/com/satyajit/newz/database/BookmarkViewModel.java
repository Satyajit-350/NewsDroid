package com.satyajit.newz.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookmarkViewModel extends AndroidViewModel {

    private BookMarksRepository bookMarksRepository;
    private LiveData<List<BookMarks>> allBookmarks;

    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        bookMarksRepository = new BookMarksRepository(application);
        allBookmarks = bookMarksRepository.getAllBookmarks();
    }

    public void insert(BookMarks bookMarks){
        bookMarksRepository.insert(bookMarks);
    }
    public void delete(BookMarks bookMarks){
        bookMarksRepository.delete(bookMarks);
    }
    public void deleteAll(){
        bookMarksRepository.deleteAll();
    }
    public LiveData<List<BookMarks>> getAllBookmarks(){
        return allBookmarks;
    }
}
