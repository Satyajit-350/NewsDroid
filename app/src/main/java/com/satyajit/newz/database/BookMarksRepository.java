package com.satyajit.newz.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookMarksRepository {

    private BooksMarksDao booksMarksDao;
    private LiveData<List<BookMarks>> allBookmarks;

    public BookMarksRepository(Application application){
        BookmarkDatabase database = BookmarkDatabase.getInstance(application);
        booksMarksDao = database.booksMarksDao();
        allBookmarks = booksMarksDao.getAllBookmarks();
    }

    public void insert(BookMarks bookMarks){
        new InsertBookmarkAsyncTask(booksMarksDao).execute(bookMarks);
    }

    public void delete(BookMarks bookMarks){
        new DeleteBookmarkAsyncTask(booksMarksDao).execute(bookMarks);
    }

    public void deleteAll(){
        new DeleteAllBookmarkAsyncTask(booksMarksDao).execute();
    }

    public LiveData<List<BookMarks>> getAllBookmarks(){
        return allBookmarks;
    }


    private static class InsertBookmarkAsyncTask extends AsyncTask<BookMarks,Void,Void>{
        private BooksMarksDao booksMarksDao;

        private InsertBookmarkAsyncTask(BooksMarksDao booksMarksDao){
            this.booksMarksDao = booksMarksDao;
        }

        @Override
        protected Void doInBackground(BookMarks... bookMarks) {
            booksMarksDao.insert(bookMarks[0]);
            return null;
        }
    }

    private static class DeleteBookmarkAsyncTask extends AsyncTask<BookMarks,Void,Void>{
        private BooksMarksDao booksMarksDao;

        private DeleteBookmarkAsyncTask(BooksMarksDao booksMarksDao){
            this.booksMarksDao = booksMarksDao;
        }

        @Override
        protected Void doInBackground(BookMarks... bookMarks) {
            booksMarksDao.delete(bookMarks[0]);
            return null;
        }
    }

    private static class DeleteAllBookmarkAsyncTask extends AsyncTask<Void,Void,Void>{
        private BooksMarksDao booksMarksDao;

        private DeleteAllBookmarkAsyncTask(BooksMarksDao booksMarksDao){
            this.booksMarksDao = booksMarksDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            booksMarksDao.deleteAll();
            return null;
        }
    }
}
