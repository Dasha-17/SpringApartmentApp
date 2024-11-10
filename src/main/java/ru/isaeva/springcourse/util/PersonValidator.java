package ru.isaeva.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.isaeva.springcourse.dao.PersonDAO;
import ru.isaeva.springcourse.models.Person;

@Controller
public class PersonValidator implements Validator{

    private final PersonDAO personDAO;
    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.show(person.getEmail()).isPresent())
        {
            errors.rejectValue("email", "", "Email уже использовался");
        }
    }
}
