package spring.library.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "library")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @Size(max = 20, min = 3, message = "Title should be from 3 to 20 letters")
    private String title;


    @Column(name = "author")
    @Pattern(regexp = "[A-z]\\w+ [A-z]\\w+", message = "Author should be as an example (Steven King) ")
    private String author;


    @Column(name = "year")
    @Min(value = 1800, message = "Year should be greater than 1800")
    @Max(value = 2022, message = "Year should be fewer than 2022")
    private int year;


    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Person owner;

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Book() {

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