package ru.isaeva.springcourse.dao;
//import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.isaeva.springcourse.models.Apartment;
import ru.isaeva.springcourse.models.Person;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Component
public class ApartmentDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ApartmentDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Apartment> index() {
        return jdbcTemplate.query("select * from Apartment", new BeanPropertyRowMapper<>(Apartment.class));
    }


    public Apartment show(int id) {
        return jdbcTemplate.query("select * from Apartment where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Apartment.class))
                .stream().findAny().orElse(null);
    }

    public void save(@Valid Apartment apartment) {
       jdbcTemplate.update("insert into Apartment(number, floor, size) values(?, ?, ?)", apartment.getNumber(), apartment.getFloor(), apartment.getSize());
    }

    public void update(int id, @Valid Apartment updatedApartment) {
     jdbcTemplate.update("update Apartment set number=?, floor=?, size=? where id=?", updatedApartment.getNumber(), updatedApartment.getFloor(), updatedApartment.getSize(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Apartment where id=?", id);
    }

   public Optional<Person> getApertmentOwner(int id) {
        return jdbcTemplate.query("select Person.* from Apartment join Person on Apartment.person_id = Person.id" +
                " where Apartment.id = ?", new Object[]{id}, new  BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
   }
   public void release(int id) {jdbcTemplate.update("update Apartment set person_id = null where id=?", id);}
   public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("update Apartment set person_id=? where id=?", selectedPerson.getId(),id);
   }
}
