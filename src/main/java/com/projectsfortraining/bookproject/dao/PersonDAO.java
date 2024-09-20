package com.projectsfortraining.bookproject.dao;

import com.projectsfortraining.bookproject.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;
    @Autowired
    public PersonDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Transactional
    public void create(Person newPerson){
        Session session = sessionFactory.getCurrentSession();
        session.save(newPerson);
    }
    @Transactional(readOnly = true)
    public Person readOne(int id){
        Session session = sessionFactory.getCurrentSession();
        return (Person) session.createQuery("SELECT p FROM Person p WHERE p.personId=:id", Person.class).setParameter("id",id);
    }
    @Transactional(readOnly = true)
    public Optional<Person> readOne(String name){
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable((Person) session.createQuery("SELECT p FROM Person p WHERE p.name=:name", Person.class).setParameter("name", name));
    }
    @Transactional(readOnly = true)
    public List<Person> readAll(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        person.setName(updatedPerson.getName());
        person.setYearOfBirth(updatedPerson.getYearOfBirth());
    }
    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        session.remove(person);
    }
}
