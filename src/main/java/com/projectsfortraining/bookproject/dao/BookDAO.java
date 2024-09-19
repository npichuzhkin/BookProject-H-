package com.projectsfortraining.bookproject.dao;

import com.projectsfortraining.bookproject.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    @Autowired
    public BookDAO(){

    }

    public void create(Book newBook){
        }

    @Transactional(readOnly = true)
    public Book readOne(int id){
        return null;
    }

    public Optional<Book> readOne(String name){
        return null;
    }

    public List<Book> readByPersonId(int personId){
        return null;
    }

    public List<Book> readAll(){
        return null;
    }

    public void update(int id, Book updatedBook){
        }

    public void appointPerson(int id, int personId){
        }

    public void delete(int id){
        }
}