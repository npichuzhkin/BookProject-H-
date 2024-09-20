package com.projectsfortraining.bookproject.controllers;

import com.projectsfortraining.bookproject.models.Book;
import com.projectsfortraining.bookproject.models.Person;
import com.projectsfortraining.bookproject.services.BooksService;
import com.projectsfortraining.bookproject.services.PeopleService;
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

    private final BooksService booksService;

    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookValidator bookValidator, BooksService booksService, PeopleService peopleService){
        this.bookValidator = bookValidator;
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String showAll(@RequestParam(value = "sort_by_year", required = false) boolean sortByYear,
                          @RequestParam(value = "page", required = false) String page,
                          @RequestParam(value = "books_per_page", required = false) String booksPerPage,
                          Model model){

        if (sortByYear && booksPerPage != null){
            model.addAttribute("books", booksService.findAll(Integer.parseInt(page),Integer.parseInt(booksPerPage),"yearOfRelease"));
            return "books/show";
        }

        if (booksPerPage != null){
            model.addAttribute("books", booksService.findAll(Integer.parseInt(page),Integer.parseInt(booksPerPage)));
            return "books/show";
        }

        if (sortByYear){
            model.addAttribute("books", booksService.findAll("yearOfRelease"));
            return "books/show";
        }

        model.addAttribute("books", booksService.findAll());
        return "books/show";
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("people", peopleService.findAll());
        if (book.getPerson()!=null) {
            model.addAttribute("person", peopleService.findOne(book.getPerson().getPersonId()));
        } else {
            person.setPersonId(0);
            model.addAttribute("person", person);
        }
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
        booksService.save(newBook);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
            Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book updatedBook, BindingResult bindingResult,
                         @PathVariable("id") int id){
        bookValidator.validate(updatedBook,bindingResult);
        if (bindingResult.hasErrors()) return "books/edit";
        booksService.update(id, updatedBook);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/person")
    public String choosePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        booksService.appointPerson(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/remove/person")
    public String removePerson(@PathVariable("id") int id){
        booksService.removePerson(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
}
