package com.projectsfortraining.bookproject.dao;

import com.projectsfortraining.bookproject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Person newPerson){
        jdbcTemplate.update("INSERT INTO Person(name, year_of_birth) VALUES (?,?) ",
                newPerson.getName(), newPerson.getYearOfBirth());
    }

    public Person readOne(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> readOne(String name){
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Person> readAll(){
        return  jdbcTemplate.query("SELECT * FROM Person WHERE person_id != 0", new BeanPropertyRowMapper<>(Person.class));
    }

    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name=?, year_of_birth=? WHERE person_id=?", updatedPerson.getName(), updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }
}
