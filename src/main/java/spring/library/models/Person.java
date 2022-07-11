package spring.library.models;


import javax.validation.Valid;
import javax.validation.constraints.*;

public class Person {
    private int id;
    @Size(min = 2,max = 15,message = "Size should be from 2 to 15")
    @NotEmpty(message = "This field should not be empty")
    private String name;

    @Size(min = 2,max = 15,message = "Size should be from 2 to 15")
    @NotEmpty(message = "This field should not be empty")
    private String surname;

    @Min(value = 1950, message = "Year should be greater than 1950")
    @Max(value = 2015, message = "Year should be fewer than 2015")
    private int year;

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
