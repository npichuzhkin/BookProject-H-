package com.projectsfortraining.bookproject.services;

import com.projectsfortraining.bookproject.models.Book;
import com.projectsfortraining.bookproject.models.Person;
import com.projectsfortraining.bookproject.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAll(String sortFieldName){
        return booksRepository.findAll(Sort.by(sortFieldName));
    }
    public List<Book> findAll(int page, int booksPerPage){
        return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

    public List<Book> findAll(int page, int booksPerPage, String sortFieldName){
        return booksRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by(sortFieldName))).getContent();
    }

    public List<Book> findAllByName(String name){
        return booksRepository.findAllByName(name);
    }

    public Book findOne(int id){
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> findByNameFirstSymbols(String symbols){
        return booksRepository.findByNameStartingWith(symbols);
    }

    public List<Book> findByPerson(Person person){
        return booksRepository.findByPerson(person);
    }

    @Transactional
    public void appointPerson(int id, Person person){
        if (booksRepository.findById(id).isPresent()){
            Book book = booksRepository.findById(id).get();
            book.setPerson(person);
            book.setTimeOfTaking(new Date());
            booksRepository.save(book);
        }
    }

    @Transactional
    public void removePerson(int id){
        if (booksRepository.findById(id).isPresent()){
            Book book = booksRepository.findById(id).get();
            book.setPerson(null);
            booksRepository.save(book);
        }
    }
    @Transactional
    public void save(Book newBook){
        booksRepository.save(newBook);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
}
