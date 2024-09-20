package com.projectsfortraining.bookproject.utils;

import com.projectsfortraining.bookproject.models.Book;
import com.projectsfortraining.bookproject.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
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
        return !(booksService.findAllByName(book.getName()).isEmpty());
    }
}
