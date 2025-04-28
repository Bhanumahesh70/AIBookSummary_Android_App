package com.example.aibooksummaryapp.Model;

public class BookSummary {
    private String title;
    private String author;
    private String summary;

    private String category;

    public BookSummary(String title, String author, String summary, String category) {
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
