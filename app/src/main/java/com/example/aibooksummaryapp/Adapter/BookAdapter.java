package com.example.aibooksummaryapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aibooksummaryapp.Model.BookSummary;
import com.example.aibooksummaryapp.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<BookSummary> bookList;
    private Context context;
    public BookAdapter(  Context context, List<BookSummary> bookList){
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
        BookSummary book = bookList.get(position);
        Log.d("BookAdapter", "Binding book: " + book.getTitle());
        holder.title.setText(book.getTitle());
        holder.author.setText("Author: "+book.getAuthor());
        holder.summary.setText("Summary: "+book.getSummary());
        holder.category.setText("Category: "+book.getCategory());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
