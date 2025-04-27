package com.example.aibooksummaryapp;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.aibooksummaryapp.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        loadBooks();
        bookAdapter = new BookAdapter(bookList);
        recyclerView.setAdapter(bookAdapter);

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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }


    private void loadBooks() {
        // Dummy data
        bookList.add(new BookSummary("Atomic Habits", "James Clear", "An easy and proven way to build good habits and break bad ones.", "Self Improvement"));
        bookList.add(new BookSummary("The Alchemist", "Paulo Coelho", "A story about following your dreams.", "Fiction"));
        bookList.add(new BookSummary("Deep Work", "Cal Newport", "Rules for focused success in a distracted world.", "Productivity"));
        bookList.add(new BookSummary("Sapiens", "Yuval Noah Harari", "A brief history of humankind.", "History"));
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

        recyclerView.setAdapter(new BookAdapter(filteredList));
    }


}