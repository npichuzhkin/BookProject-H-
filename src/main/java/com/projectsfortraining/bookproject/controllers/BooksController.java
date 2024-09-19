package com.projectsfortraining.bookproject.controllers;

import com.projectsfortraining.bookproject.dao.BookDAO;
import com.projectsfortraining.bookproject.dao.PersonDAO;
import com.projectsfortraining.bookproject.models.Book;
import com.projectsfortraining.bookproject.models.Person;
import com.projectsfortraining.bookproject.utils.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookValidator bookValidator;
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookValidator bookValidator, BookDAO bookDAO, PersonDAO personDAO){
        this.bookValidator = bookValidator;

        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String showAll(Model model){
        model.addAttribute("books", bookDAO.readAll());
        return "books/show";
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        Book book = bookDAO.readOne(id);
        model.addAttribute("book", book);
        model.addAttribute("people", personDAO.readAll());
        model.addAttribute("person", personDAO.readOne(book.getPersonId()));
        return "books/book";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book newBook, BindingResult bindingResult){
        bookValidator.validate(newBook,bindingResult);
        if (bindingResult.hasErrors()) return "books/new";
        bookDAO.create(newBook);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
            Model model){
        model.addAttribute("book", bookDAO.readOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book updatedBook, BindingResult bindingResult,
                         @PathVariable("id") int id){
        bookValidator.validate(updatedBook,bindingResult);
        if (bindingResult.hasErrors()) return "books/edit";
        bookDAO.update(id, updatedBook);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/person")
    public String choosePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookDAO.appointPerson(id, person.getPersonId());
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
