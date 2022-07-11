package spring.library.models;

import javax.validation.constraints.*;

public class Book {
    private int id;

    @Size(max = 20,min = 3,message = "Title should be from 3 to 20 letters")
    private String title;

    @Pattern(regexp = "[A-z]\\w+ [A-z]\\w+", message = "Author should be as an example (Steven King) ")
    private String author;

    @Min(value = 1800, message = "Year should be greater than 1800")
    @Max(value = 2022, message = "Year should be fewer than 2022")
    private int year;


    public Book(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }
}