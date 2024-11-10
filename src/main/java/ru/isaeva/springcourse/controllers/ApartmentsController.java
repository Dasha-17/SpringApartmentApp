package ru.isaeva.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isaeva.springcourse.dao.ApartmentDAO;
import ru.isaeva.springcourse.dao.PersonDAO;
import ru.isaeva.springcourse.models.Apartment;
import ru.isaeva.springcourse.models.Person;
import ru.isaeva.springcourse.util.PersonValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/apartments")
public class ApartmentsController {

    private final ApartmentDAO apartmentDAO;
    private final PersonDAO personDAO;

    @Autowired
    public ApartmentsController(ApartmentDAO apartmentDAO, PersonDAO personDAO, PersonValidator personValidator) {
        this.apartmentDAO = apartmentDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("apartments", apartmentDAO.index());
        return "apartments/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("apartment", apartmentDAO.show(id));

        Optional<Person> apartmentOwner = apartmentDAO.getApertmentOwner(id);
        if (apartmentOwner.isPresent())
            model.addAttribute("owner", apartmentOwner.get());
        else
            model.addAttribute("people", personDAO.index());

        return "apartments/show";
    }

    @GetMapping("/new")
    public String newApartment(@ModelAttribute("apartment") Apartment apartment) {
        return "apartments/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("apartment") @Valid Apartment apartment,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "apartments/new";

        apartmentDAO.save(apartment);
        return "redirect:/apartments";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("apartment", apartmentDAO.show(id));
        return "apartments/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("apartment") @Valid Apartment apartment, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "apartments/edit";

        apartmentDAO.update(id, apartment);
        return "redirect:/apartments";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        apartmentDAO.delete(id);
        return "redirect:/apartments";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        apartmentDAO.release(id);
        return "redirect:/apartments/" + id;
    }
    @PatchMapping("/{id}/assign")
    public String assign (@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        apartmentDAO.assign(id, selectedPerson);
        return "redirect:/apartments/" + id;
    }
}