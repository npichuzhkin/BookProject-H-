package com.projectsfortraining.bookproject.controllers;

import com.projectsfortraining.bookproject.models.Person;
import com.projectsfortraining.bookproject.services.BooksService;
import com.projectsfortraining.bookproject.services.PeopleService;
import com.projectsfortraining.bookproject.utils.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonValidator personValidator;

    private final BooksService booksService;

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PersonValidator personValidator, BooksService booksService, PeopleService peopleService){
        this.personValidator = personValidator;
        this.booksService = booksService;

        this.peopleService = peopleService;
    }

    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/show";
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable("id") int id, Model model){
        Person person = peopleService.findOne(id);
        model.addAttribute("person", person);
        model.addAttribute("books", booksService.findByPerson(person));
        return "people/person";
    }

    @GetMapping("/new")
    public String addPerson(@ModelAttribute("person") Person newPerson){
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/new";
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("person") @Valid Person updatedPerson,
                         BindingResult bindingResult){
        personValidator.validate(updatedPerson, bindingResult);
        if (bindingResult.hasErrors()) return "people/edit";
        peopleService.update(id, updatedPerson);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }


}
