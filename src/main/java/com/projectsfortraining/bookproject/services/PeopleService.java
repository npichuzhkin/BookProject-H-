package com.projectsfortraining.bookproject.services;

import com.projectsfortraining.bookproject.models.Person;
import com.projectsfortraining.bookproject.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll().stream().filter(p -> p.getPersonId()!=0).collect(Collectors.toList());
    }

    public List<Person> findAll(String name){
        return peopleRepository.findAllByName(name).stream().filter(person -> person.getPersonId()!=0).collect(Collectors.toList());
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person newPerson){
        peopleRepository.save(newPerson);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setPersonId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
