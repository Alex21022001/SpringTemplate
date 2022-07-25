package spring.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.library.models.Book;
import spring.library.models.Person;
import spring.library.services.BooksService;
import spring.library.services.PeopleService;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final BooksService booksService;


    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model, @ModelAttribute("book") Book book) {
        model.addAttribute("person", peopleService.findById(id));
        List<Book> books = peopleService.getBookByPersonId(id);
        if (!books.isEmpty()) {
            model.addAttribute("personbooks", books);
        } else {
            model.addAttribute("books", booksService.findFreeBook());
        }
        return "people/show";
    }
    @PostMapping("/{id}/set")
    public String takeBook(@PathVariable int id,@ModelAttribute("book")Book book){
       peopleService.addOwner(id,book);
        return "redirect:/people";
    }
    @PatchMapping("/{id}/set")
    public String returnBook(@PathVariable int id){
        booksService.deleteOwner(id);
        return "redirect:/people";
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

        peopleService.save(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String changePerson(@PathVariable int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPersonPage(@PathVariable int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
