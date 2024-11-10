package ru.isaeva.springcourse.dao;
//import org.checkerframework.checker.units.qual.A;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.isaeva.springcourse.models.Apartment;
import ru.isaeva.springcourse.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("select * from Person where email=?", new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }


    public Person show(int id) {
        return jdbcTemplate.query("select * from Person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into Person(name, age, email) values(?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update Person set name=?, age=?, email=? where id=?", updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Person where id=?", id);
    }

    public Optional<Person> getPersonByEmail(String email){
        return jdbcTemplate.query("select * from Apartment where email=?", new Object[]{email},
        new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public List<Apartment> getApartmentByPersonId(int id) {
        return jdbcTemplate.query("select * from Apartment where person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Apartment.class));
    }

}