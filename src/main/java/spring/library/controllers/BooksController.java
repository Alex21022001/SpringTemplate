package spring.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.library.dao.BookDAO;
import spring.library.dao.PersonDAO;
import spring.library.models.Book;
import spring.library.models.Person;
import spring.library.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "library/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personDAO.index());
        }

        return "library/show";
    }

    @GetMapping("/new")
    public String pageForCreateNewBook(@ModelAttribute("book") Book book) {
        return "library/new";
    }

    @PostMapping()
    public String addNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()) {
            return "library/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        return "library/edit";
    }

    @PatchMapping("/edit/{id}")
    public String edit(@ModelAttribute("book")@Valid Book book, @PathVariable int id,BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "library/edit";
        }
        bookDAO.update(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        bookDAO.deletePerson(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/set")
    public String setPerson(@PathVariable int id, @ModelAttribute("person") Person person) {
        bookDAO.addPerson(id, person);
        return "redirect:/books";
    }

}
