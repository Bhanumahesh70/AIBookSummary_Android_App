package com.example.aibooksummaryapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aibooksummaryapp.Model.Book;
import com.example.aibooksummaryapp.Model.VolumeInfo;
import com.example.aibooksummaryapp.R;
import com.example.aibooksummaryapp.Repository.GeminiRepository;
import com.example.aibooksummaryapp.Repository.OpenAiRepository;
import com.google.gson.Gson;

public class BookDetailActivity extends BaseNavActivity {

    private TextView titleView, subtitleView, metaView, categoryView, descriptionView, summaryResult;
    private ImageView bookCover;
    private Button previewButton, infoButton, summaryButton;
    private OpenAiRepository openAiRepository;
    private GeminiRepository geminiRepository;
    private static final String OPENAI_API_KEY = "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";

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
        summaryButton = findViewById(R.id.summarybutton);
        summaryResult = findViewById(R.id.summary_result);

        String bookJson = getIntent().getStringExtra("book");
        // Setup OpenAI Repository
        openAiRepository = new OpenAiRepository(OPENAI_API_KEY);
        geminiRepository = new GeminiRepository("AIzaSyB1SjbqoHZazyW5yEUAnnkk5Pyl2TIGLjA");

        if (bookJson != null) {
            Book book = new Gson().fromJson(bookJson, Book.class);
            VolumeInfo info = book.getVolumeInfo();

            // Load title, subtitle
            String bookTitle = info.getTitle();
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
            String thumbnailUrl = info.getImageLinks().getSmallThumbnail();
            if (thumbnailUrl != null && thumbnailUrl.startsWith("http://")) {
                thumbnailUrl = thumbnailUrl.replace("http://", "https://");
            }

            Glide.with(this)
                    .load(thumbnailUrl)
                    .into(bookCover);
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

            /*
            summaryButton.setOnClickListener(v -> {
                summaryResult.setText("Generating summary...");

                openAiRepository.getBookSummary(bookTitle, new OpenAiRepository.SummaryCallback() {
                    @Override
                    public void onSummaryReceived(String summary) {
                        runOnUiThread(() -> summaryResult.setText(summary));
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> {
                            summaryResult.setText("Failed to fetch summary.");
                            Toast.makeText(BookDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            });
            */
            summaryButton.setOnClickListener(v -> {
                summaryResult.setText("Generating summary...");

                geminiRepository.getBookSummary(bookTitle, new OpenAiRepository.SummaryCallback() {
                    @Override
                    public void onSummaryReceived(String summary) {
                        runOnUiThread(() -> summaryResult.setText(summary));
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> {
                            summaryResult.setText("Failed to fetch summary.");
                            Toast.makeText(BookDetailActivity.this, error, Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            });
        }
    }
}
