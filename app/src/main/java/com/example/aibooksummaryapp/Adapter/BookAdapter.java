package com.example.aibooksummaryapp.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aibooksummaryapp.Model.Book;
import com.example.aibooksummaryapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private Context context;
    public BookAdapter(  Context context, List<Book> bookList){
        this.bookList = bookList;
        this.context = context;

    }

     public static class BookViewHolder extends RecyclerView.ViewHolder {
         TextView title, author, summary, category;
         Button saveButton;

         public BookViewHolder (View itemView){
             super(itemView);
             title = itemView.findViewById(R.id.book_title);
             author = itemView.findViewById(R.id.book_author);
             summary = itemView.findViewById(R.id.book_summary);
             category = itemView.findViewById(R.id.book_category);
             saveButton = itemView.findViewById(R.id.saveButton);
         }

     }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_summary, parent, false);
         return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        Log.d("BookAdapter", "Binding book: " + book.getVolumeInfo().getTitle());
        holder.title.setText(book.getVolumeInfo().getTitle());


        // Handle Authors
        if (book.getVolumeInfo().getAuthors() != null && !book.getVolumeInfo().getAuthors().isEmpty()) {
            StringBuilder authorsBuilder = new StringBuilder();
            for (String author : book.getVolumeInfo().getAuthors()) {
                authorsBuilder.append(author).append("\n");
            }
            holder.author.setText( authorsBuilder.toString().trim());
        } else {
            holder.author.setText("Unknown");
        }

        // Handle Categories
        if (book.getVolumeInfo().getCategories() != null && !book.getVolumeInfo().getCategories().isEmpty()) {
            StringBuilder categoriesBuilder = new StringBuilder();
            for (String category : book.getVolumeInfo().getCategories()) {
                categoriesBuilder.append(category).append("\n");
            }
            holder.category.setText(categoriesBuilder.toString().trim());
        } else {
            holder.category.setText(" Unknown");
        }

        // Handle Summary
        if (book.getVolumeInfo().getDescription() != null && !book.getVolumeInfo().getDescription().isEmpty()) {
            holder.summary.setText( book.getVolumeInfo().getDescription());
        } else {
            holder.summary.setText("Summary: No summary available");
        }

        holder.saveButton.setOnClickListener(v -> {
            saveBookToFirebase(context, book);
        });
    }
    private void saveBookToFirebase(Context context, Book book) {

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("SavedBooks");

        String bookId = book.getId();
        databaseRef.child(bookId).setValue(book)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Book Saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Failed to Save Book", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateBooks(List<Book> newBookList) {
        bookList.clear();
        bookList.addAll(newBookList);
        notifyDataSetChanged();
    }
}
