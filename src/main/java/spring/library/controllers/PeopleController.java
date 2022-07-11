package spring.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.library.dao.BookDAO;
import spring.library.dao.PersonDAO;
import spring.library.models.Person;


import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;


    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;

        this.bookDAO = bookDAO;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
       model.addAttribute("person",personDAO.show(id));
       model.addAttribute("personListOfBooks",bookDAO.showListOfPersonalBook(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String changePerson(@PathVariable int id,@ModelAttribute("person")@Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        personDAO.changePerson(person,id);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPersonPage(@PathVariable int  id,Model model){
     model.addAttribute("person",personDAO.show(id));
     return "people/edit";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        personDAO.delete(id);
        return "redirect:/people";
    }

}
