package com.projectsfortraining.bookproject.models;

//import jakarta.persistence.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Duration;
import java.time.Period;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person person;
    @NotEmpty(message = "Название книги должно быть заполнено")
    @Size(min = 2, max = 100, message = "Название книги должно содержать от 2 до 100 символов")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Автор должен быть заполнен")
    @Size(min=2, max=200, message = "Имя автора должно содержать от 2 до 200 символов")
    @Column(name = "author")
    private String author;
    @Max(value = 3000, message = "Год должен быть целым числом и не превышать 3000")
    @Positive(message = "Год должен иметь положительное значение")
    @Column(name = "year_of_release")
    private int yearOfRelease;

    @Column(name = "time_of_taking")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfTaking;

    public Book (){}

    public Book(String name, String author, int yearOfRelease) {
        this.name = name;
        this.author = author;
        this.yearOfRelease = yearOfRelease;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Date getTimeOfTaking() {
        return timeOfTaking;
    }

    public void setTimeOfTaking(Date timeOfTaking) {
        this.timeOfTaking = timeOfTaking;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", person=" + person +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                '}';
    }

    public boolean checkDelay(){
        Date currentDate = new Date();
        Duration duration = Duration.between(timeOfTaking.toInstant(),currentDate.toInstant());
        return duration.toDays() >= 10;
    }
}
