package com.example.aibooksummaryapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aibooksummaryapp.Adapter.BookAdapter;
import com.example.aibooksummaryapp.Model.Book;
import com.example.aibooksummaryapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SavedBooksActivity extends BaseNavActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> savedBooksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  bottomNavigationView.setSelectedItemId(R.id.nav_saved);

        // Inflate Saved Books layout inside the container_frame
        View savedBooksView = getLayoutInflater().inflate(R.layout.activity_saved_books, frameLayout, true);
        recyclerView = savedBooksView.findViewById(R.id.recyclerViewSaved);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        savedBooksList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, savedBooksList,true,  book -> {
            Intent intent = new Intent(SavedBooksActivity.this, BookDetailActivity.class);
            intent.putExtra("book", new Gson().toJson(book)); // Pass as JSON string
            startActivity(intent);
        });
        recyclerView.setAdapter(bookAdapter);

        fetchSavedBooks();
    }

    private void fetchSavedBooks() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("SavedBooks");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                savedBooksList.clear();
                for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                    Book book = bookSnapshot.getValue(Book.class);
                    savedBooksList.add(book);
                }

                Log.d("SavedBooksActivity", "Number of saved books: " + savedBooksList.size());
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(SavedBooksActivity.this, "Failed to load saved books", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onSearchQuerySubmitted(String query) {

        List<Book> filteredList = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Book book : savedBooksList) {
            boolean matchesTitle = book.getVolumeInfo().getTitle() != null &&
                    book.getVolumeInfo().getTitle().toLowerCase().contains(lowerQuery);

            boolean matchesAuthor = book.getVolumeInfo().getAuthors() != null &&
                    !book.getVolumeInfo().getAuthors().isEmpty() && containsIgnoreCase(book.getVolumeInfo().getAuthors(), lowerQuery);

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

        bookAdapter = new BookAdapter(this, filteredList,false, book -> {
            Intent intent = new Intent(SavedBooksActivity.this, BookDetailActivity.class);
            intent.putExtra("book", new Gson().toJson(book)); // Pass as JSON string
            startActivity(intent);
        });
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();


    }
    private boolean containsIgnoreCase(List<String> list, String query) {
        for (String item : list) {
            if (item != null && item.toLowerCase().contains(query)) {
                return true;
            }
        }
        return false;
    }
}