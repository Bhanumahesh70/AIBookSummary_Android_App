package com.example.aibooksummaryapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aibooksummaryapp.Model.Book;
import com.example.aibooksummaryapp.Model.VolumeInfo;
import com.example.aibooksummaryapp.R;
import com.google.gson.Gson;

public class BookDetailActivity extends BaseNavActivity {

    private TextView titleView, subtitleView, metaView, categoryView, descriptionView;
    private ImageView bookCover;
    private Button previewButton, infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View detailView = getLayoutInflater().inflate(R.layout.activity_book_detail, frameLayout, true);

        // Bind Views
        bookCover = findViewById(R.id.bookcover);
        titleView = findViewById(R.id.detail_title);
        subtitleView = findViewById(R.id.detailsubtitle);
        metaView = findViewById(R.id.detailmeta);
        categoryView = findViewById(R.id.detail_categories);
        descriptionView = findViewById(R.id.detail_description);
        previewButton = findViewById(R.id.previewbutton);
        infoButton = findViewById(R.id.infobutton);

        String bookJson = getIntent().getStringExtra("book");

        if (bookJson != null) {
            Book book = new Gson().fromJson(bookJson, Book.class);
            VolumeInfo info = book.getVolumeInfo();

            // Load title, subtitle
            titleView.setText(info.getTitle());
            subtitleView.setText(info.getSubtitle() != null ? info.getSubtitle() : "");

            // Author • Publisher • Date
            String authors = info.getAuthors() != null ? TextUtils.join(", ", info.getAuthors()) : "Unknown Author";
            String publisher = info.getPublisher() != null ? info.getPublisher() : "Unknown Publisher";
            String publishedDate = info.getPublishedDate() != null ? info.getPublishedDate() : "N/A";
            metaView.setText("By " + authors + " • " + publisher + " • " + publishedDate);

            // Categories
            categoryView.setText(info.getCategories() != null
                    ? "Categories: " + TextUtils.join(", ", info.getCategories())
                    : "Categories: N/A");

            // Description
            descriptionView.setText(info.getDescription() != null ? info.getDescription() : "No description available.");

            // Book cover image
            if (info.getImageLinks() != null && info.getImageLinks().getThumbnail() != null) {
                Glide.with(this)
                        .load(info.getImageLinks().getThumbnail())
                        .into(bookCover);
            } else {
               // bookCover.setImageResource(R.drawable.placeholder_image);
            }

            // Preview button
            if (info.getPreviewLink() != null) {
                previewButton.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(info.getPreviewLink()));
                    startActivity(intent);
                });
            }

            // More info button
            if (info.getInfoLink() != null) {
                infoButton.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(info.getInfoLink()));
                    startActivity(intent);
                });
            }
        }
    }
}
