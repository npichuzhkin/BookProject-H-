package com.projectsfortraining.bookproject.utils;

import com.projectsfortraining.bookproject.dao.BookDAO;
import com.projectsfortraining.bookproject.models.Book;
import com.projectsfortraining.bookproject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if(suchNameExists(book)){
            errors.rejectValue("name", "","Книга с таким названием уже существует");
        }
    }

    private boolean suchNameExists(Book book){
        return bookDAO.readOne(book.getName()).isPresent();
    }
}
