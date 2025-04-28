package com.example.aibooksummaryapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aibooksummaryapp.Model.Book;
import com.example.aibooksummaryapp.R;

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

         public BookViewHolder (View itemView){
             super(itemView);
             title = itemView.findViewById(R.id.book_title);
             author = itemView.findViewById(R.id.book_author);
             summary = itemView.findViewById(R.id.book_summary);
             category = itemView.findViewById(R.id.book_category);
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
        holder.summary.setText("Summary: "+book.getVolumeInfo().getDescription());

        if (book.getVolumeInfo().getAuthors() != null && !book.getVolumeInfo().getAuthors().isEmpty()) {
            holder.author.setText("Author: " + book.getVolumeInfo().getAuthors().get(0)); // Only first author
        } else {
            holder.author.setText("Author: Unknown");
        }
        if (book.getVolumeInfo().getCategories() != null && !book.getVolumeInfo().getCategories().isEmpty()) {
            holder.category.setText("Category: " + book.getVolumeInfo().getCategories().get(0)); // Only first category
        } else {
            holder.category.setText("Category: Unknown");
        }
        holder.summary.setText(book.getVolumeInfo().getDescription() != null ? book.getVolumeInfo().getDescription() : "No summary available");
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
