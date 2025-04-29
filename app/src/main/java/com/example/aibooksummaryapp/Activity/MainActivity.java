package com.example.aibooksummaryapp.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.aibooksummaryapp.Adapter.BookAdapter;
import com.example.aibooksummaryapp.Model.Book;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aibooksummaryapp.R;
import com.example.aibooksummaryapp.ViewModel.BookViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseNavActivity {


    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookViewModel bookViewModel;
    private List<Book> bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate Home layout inside the main Activity layout
       // View homeView = LayoutInflater.from(this).inflate(R.layout.activity_home, frameLayout, true);
        View homeView = getLayoutInflater().inflate(R.layout.activity_home, frameLayout, true);
        recyclerView = homeView.findViewById(R.id.recyclerViewHome);
        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookList = new ArrayList<>();
        Log.d("MainActivity","Loading books....");
       // loadBooks();
        bookAdapter = new BookAdapter(this, bookList);
        bookAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(bookAdapter);

        //Get book details
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        // Observe book list
        bookViewModel.getBooks().observe(this, books -> {
            if (books != null) {
                bookAdapter.updateBooks(books);
            }
        });

        // Fetch recommended books
        String query = "recommended";
        bookViewModel.fetchRecommendedBooks(query);

    }
    @Override
    protected void onSearchQuerySubmitted(String query) {
        bookViewModel.fetchRecommendedBooks(query);
         /*
        List<Book> filteredList = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Book book : bookList) {
            boolean matchesTitle = book.getVolumeInfo().getTitle() != null &&
                    book.getVolumeInfo().getTitle().toLowerCase().contains(lowerQuery);

            boolean matchesAuthor = book.getVolumeInfo().getAuthors() != null &&
                    !book.getVolumeInfo().getAuthors().isEmpty() &&
                    containsIgnoreCase(book.getVolumeInfo().getAuthors(), lowerQuery);

            boolean matchesCategory = book.getVolumeInfo().getCategories() != null &&
                    !book.getVolumeInfo().getCategories().isEmpty() &&
                    containsIgnoreCase(book.getVolumeInfo().getCategories(), lowerQuery);

            if (matchesTitle || matchesAuthor || matchesCategory) {
                filteredList.add(book);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No books found", Toast.LENGTH_SHORT).show();
        }

        bookAdapter = new BookAdapter(this, filteredList);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();

         */
    }

    private void loadBooks() {
        // Dummy data
//        bookList.add(new Book("Atomic Habits", "James Clear", "An easy and proven way to build good habits and break bad ones.", "Self Improvement"));
//        bookList.add(new Book("The Alchemist", "Paulo Coelho", "A story about following your dreams.", "Fiction"));
//        bookList.add(new Book("Deep Work", "Cal Newport", "Rules for focused success in a distracted world.", "Productivity"));
//        bookList.add(new Book("Sapiens", "Yuval Noah Harari", "A brief history of humankind.", "History"));
//        bookList.add(new Book("The Power of Habit", "Charles Duhigg", "The science of habit formation and how to change them.", "Self Improvement"));
//        bookList.add(new Book("Educated", "Tara Westover", "A memoir of a woman who escapes her survivalist family to pursue education.", "Biography"));
//        bookList.add(new Book("The Subtle Art of Not Giving a F*ck", "Mark Manson", "A counterintuitive guide to living a good life.", "Self Improvement"));
//        bookList.add(new Book("Thinking, Fast and Slow", "Daniel Kahneman", "A groundbreaking look at how we think and make decisions.", "Psychology"));
//        bookList.add(new Book("Becoming", "Michelle Obama", "A deeply personal memoir by the former First Lady.", "Biography"));
//        bookList.add(new Book("The Four-Hour Workweek", "Tim Ferriss", "Escape the 9-5, live anywhere, and join the new rich.", "Productivity"));
//        bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "A story about the American dream and its disillusionment.", "Fiction"));
//        bookList.add(new Book("1984", "George Orwell", "A dystopian novel that explores totalitarianism and surveillance.", "Fiction"));
//        bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", "A novel about racial injustice and moral growth in the Deep South.", "Fiction"));
//        bookList.add(new Book("Quiet", "Susan Cain", "The power of introverts in a world that can't stop talking.", "Psychology"));
//        Log.d("MainActivity", "Books loaded: " + bookList.size());
    }


}