package com.example.aibooksummaryapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.aibooksummaryapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;



public class BaseNavActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected SearchView searchView;
    protected BottomNavigationView bottomNavigationView;
    protected MaterialToolbar topAppBar;
    protected FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        searchView = findViewById(R.id.search_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        topAppBar = findViewById(R.id.topAppBar);
        frameLayout = findViewById(R.id.container_frame);
        setupDrawer();
        setupBottomNavigation();
        setupTopAppBar();
        setupSearchView();

    }

    private void setupDrawer() {
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
            String selectedCategory = null;
            int itemId = item.getItemId();

            if (itemId == R.id.category_action) {
                selectedCategory = "Action";
            } else if (itemId == R.id.category_adventure) {
                selectedCategory = "Adventure";
            } else if (itemId == R.id.category_fantasy) {
                selectedCategory = "Fantasy";
            } else if (itemId == R.id.category_fiction) {
                selectedCategory = "Fiction";
            } else if (itemId == R.id.category_nonfiction) {
                selectedCategory = "Non-fiction";
            } else if (itemId == R.id.category_biography) {
                selectedCategory = "Biography";
            } else if (itemId == R.id.category_history) {
                selectedCategory = "History";
            } else if (itemId == R.id.category_romance) {
                selectedCategory = "Romance";
            } else if (itemId == R.id.category_thriller) {
                selectedCategory = "Thriller";
            } else if (itemId == R.id.category_scifi) {
                selectedCategory = "Science Fiction";
            } else if (itemId == R.id.category_mystery) {
                selectedCategory = "Mystery";
            } else if (itemId == R.id.category_poetry) {
                selectedCategory = "Poetry";
            } else if (itemId == R.id.category_comics) {
                selectedCategory = "Comics";
            } else if (itemId == R.id.category_philosophy) {
                selectedCategory = "Philosophy";
            } else if (itemId == R.id.category_education) {
                selectedCategory = "Education";
            } else if (itemId == R.id.category_health) {
                selectedCategory = "Health";
            } else if (itemId == R.id.category_cooking) {
                selectedCategory = "Cooking";
            } else if (itemId == R.id.category_travel) {
                selectedCategory = "Travel";
            } else if (itemId == R.id.category_music) {
                selectedCategory = "Music";
            } else if (itemId == R.id.category_art) {
                selectedCategory = "Art";
            } else if (itemId == R.id.category_science) {
                selectedCategory = "Science";
            } else if (itemId == R.id.category_computers) {
                selectedCategory = "Computers";
            } else if (itemId == R.id.category_psychology) {
                selectedCategory = "Psychology";
            } else if (itemId == R.id.category_sports) {
                selectedCategory = "Sports";
            } else if (itemId == R.id.category_law) {
                selectedCategory = "Law";
            } else if (itemId == R.id.category_religion) {
                selectedCategory = "Religion";
            } else if (itemId == R.id.category_medical) {
                selectedCategory = "Medical";
            } else if (itemId == R.id.category_mathematics) {
                selectedCategory = "Mathematics";
            } else if (itemId == R.id.category_selfhelp) {
                selectedCategory = "Self-Help";
            }

            if (selectedCategory != null) {
                onCategorySelected(selectedCategory);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
    protected void onCategorySelected(String category) {}
    private void setupBottomNavigation(){
        // Bottom Navigation Item Clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                //Toast.makeText(BaseNavActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BaseNavActivity.this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.nav_saved)  {
                //Toast.makeText(BaseNavActivity.this, "Saved Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BaseNavActivity.this, SavedBooksActivity.class));
                return true;

            }/*
            else if (item.getItemId() == R.id.nav_recommend) {
                //Toast.makeText(MainActivity.this, "Recommend Selected", Toast.LENGTH_SHORT).show();
                return true;
            }*/
            return false;
        });
    }

    private void setupTopAppBar(){

        // Top App bar clicks
        topAppBar.setOnMenuItemClickListener(item -> {
            /*
            if (item.getItemId() == R.id.action_search) {
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.action_profile)  {
                Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }*/
            return false;
        });
    }

    private void setupSearchView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearchQuerySubmitted(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onSearchQuerySubmitted(newText);
                return true;
            }
        });
    }

    protected void onSearchQuerySubmitted(String query) {

    }
}