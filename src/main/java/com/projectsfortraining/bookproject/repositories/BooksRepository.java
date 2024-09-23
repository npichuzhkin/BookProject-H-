package com.projectsfortraining.bookproject.repositories;

import com.projectsfortraining.bookproject.models.Book;
import com.projectsfortraining.bookproject.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByPerson(Person person);

    List<Book> findAllByName(String name);

    List<Book> findByNameStartingWith(String s);
}
