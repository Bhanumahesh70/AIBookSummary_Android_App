package com.example.aibooksummaryapp.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aibooksummaryapp.Adapter.BookAdapter;
import com.example.aibooksummaryapp.Model.BookSummary;

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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookViewModel bookViewModel;
    private List<BookSummary> bookList;
    private SearchView searchView;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        topAppBar = findViewById(R.id.topAppBar);

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
        String query = "recommended";  // Example query
        bookViewModel.fetchRecommendedBooks(query);

        // Setup Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, topAppBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Navigation Item Clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.drawer_home) {
                Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.drawer_about) {
                Toast.makeText(this, "About Clicked", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(navigationView);
            return true;
        });

        // Bottom Navigation Item Clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                 Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.nav_saved)  {
                 Toast.makeText(MainActivity.this, "Saved Selected", Toast.LENGTH_SHORT).show();
                 return true;

            }
            else if (item.getItemId() == R.id.nav_recommend) {
                Toast.makeText(MainActivity.this, "Recommend Selected", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // Top App bar clicks
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_search) {
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.action_profile)  {
                Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // SearchView Handling
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBooks(newText);
                return true;
            }
        });


    }


    private void loadBooks() {
        // Dummy data
        bookList.add(new BookSummary("Atomic Habits", "James Clear", "An easy and proven way to build good habits and break bad ones.", "Self Improvement"));
        bookList.add(new BookSummary("The Alchemist", "Paulo Coelho", "A story about following your dreams.", "Fiction"));
        bookList.add(new BookSummary("Deep Work", "Cal Newport", "Rules for focused success in a distracted world.", "Productivity"));
        bookList.add(new BookSummary("Sapiens", "Yuval Noah Harari", "A brief history of humankind.", "History"));
        bookList.add(new BookSummary("The Power of Habit", "Charles Duhigg", "The science of habit formation and how to change them.", "Self Improvement"));
        bookList.add(new BookSummary("Educated", "Tara Westover", "A memoir of a woman who escapes her survivalist family to pursue education.", "Biography"));
        bookList.add(new BookSummary("The Subtle Art of Not Giving a F*ck", "Mark Manson", "A counterintuitive guide to living a good life.", "Self Improvement"));
        bookList.add(new BookSummary("Thinking, Fast and Slow", "Daniel Kahneman", "A groundbreaking look at how we think and make decisions.", "Psychology"));
        bookList.add(new BookSummary("Becoming", "Michelle Obama", "A deeply personal memoir by the former First Lady.", "Biography"));
        bookList.add(new BookSummary("The Four-Hour Workweek", "Tim Ferriss", "Escape the 9-5, live anywhere, and join the new rich.", "Productivity"));
        bookList.add(new BookSummary("The Great Gatsby", "F. Scott Fitzgerald", "A story about the American dream and its disillusionment.", "Fiction"));
        bookList.add(new BookSummary("1984", "George Orwell", "A dystopian novel that explores totalitarianism and surveillance.", "Fiction"));
        bookList.add(new BookSummary("To Kill a Mockingbird", "Harper Lee", "A novel about racial injustice and moral growth in the Deep South.", "Fiction"));
        bookList.add(new BookSummary("Quiet", "Susan Cain", "The power of introverts in a world that can't stop talking.", "Psychology"));
        Log.d("MainActivity", "Books loaded: " + bookList.size());
    }

    private  void filterBooks(String query) {
        List<BookSummary> filteredList = new ArrayList<>();
        for (BookSummary book : bookList) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                    book.getCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(book);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No books found", Toast.LENGTH_SHORT).show();
        }

        bookAdapter = new BookAdapter(this,filteredList);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }


}