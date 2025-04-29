package com.example.aibooksummaryapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aibooksummaryapp.Model.Book;
import com.example.aibooksummaryapp.Repository.BookRepository;

import java.util.List;

public class BookViewModel extends ViewModel {
    private MutableLiveData<List<Book>> bookList;
    private BookRepository bookRepository;

    public BookViewModel() {
        bookRepository = new BookRepository();
        bookList = new MutableLiveData<>();
    }

    public LiveData<List<Book>> getBooks() {
        return bookList;
    }

    public void fetchBooks(String query) {
        bookRepository.getBooks(query, new BookRepository.BookListCallback() {
            @Override
            public void onBooksFetched(List<Book> books) {
                bookList.setValue(books);
            }

            @Override
            public void onError(String error) {
                Log.e("BookViewModel", error);
            }
        });
    }
    public void fetchBooksByCategory(String category) {
        String query = "subject:" + category;
        fetchBooks(query);
    }
}