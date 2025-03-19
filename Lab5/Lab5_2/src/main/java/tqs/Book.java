package tqs;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private LocalDate published;

    public Book(String title, String author, LocalDate published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublished() {
        return published;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Published: " + published;
    }
}
