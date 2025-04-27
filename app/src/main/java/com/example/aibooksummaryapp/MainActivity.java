package com.example.aibooksummaryapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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


    }

    private void loadBooks() {
        // Dummy data
        bookList.add(new BookSummary("Atomic Habits", "James Clear", "An easy and proven way to build good habits and break bad ones.", "Self Improvement"));
        bookList.add(new BookSummary("The Alchemist", "Paulo Coelho", "A story about following your dreams.", "Fiction"));
        bookList.add(new BookSummary("Deep Work", "Cal Newport", "Rules for focused success in a distracted world.", "Productivity"));
        bookList.add(new BookSummary("Sapiens", "Yuval Noah Harari", "A brief history of humankind.", "History"));
        // Add more books if you want
    }


}