package spring.library.models;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 2,max = 15,message = "Size should be from 2 to 15")
    @NotEmpty(message = "This field should not be empty")
    private String name;

    @Column(name = "surname")
    @Size(min = 2,max = 15,message = "Size should be from 2 to 15")
    @NotEmpty(message = "This field should not be empty")
    private String surname;

    @Column(name = "year")
    @Min(value = 1950, message = "Year should be greater than 1950")
    @Max(value = 2015, message = "Year should be fewer than 2015")
    private int year;

    @OneToMany(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person(int id, String name, String surname, int year) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.year = year;
    }
}
