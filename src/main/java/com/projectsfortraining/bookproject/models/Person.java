package com.projectsfortraining.bookproject.models;


//import jakarta.persistence.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;
    @NotEmpty(message = "ФИО должно быть заполнено")
    @Size(min=2, max=200, message = "ФИО должно содержать от 2 до 200 символов")
    @Pattern(regexp = "[А-Я].* [А-Я].* [А-Я].*", message = "ФИО должно быть следующего формата: Фамилия Имя Отчество")
    @Column(name = "name")
    private String name;

    @Max(value = 3000, message = "Год должен быть целым числом и не превышать 3000")
    @Positive(message = "Год должен иметь положительное значение")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "person",fetch = FetchType.LAZY)
    private List<Book> books;

    public Person (){}

    public Person(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}
