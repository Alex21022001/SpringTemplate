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
import spring.library.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookValidator bookValidator;
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookValidator bookValidator, BooksService booksService, PeopleService peopleService) {
        this.bookValidator = bookValidator;
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(name = "sort_by_year", required = false) boolean sorted ) {
        if (page == null || booksPerPage == null)
            model.addAttribute("books", booksService.findAll(sorted));
        else
            model.addAttribute("books", booksService.getPageWithParam(page, booksPerPage, sorted));

        return "library/index";
    }
    @PatchMapping()
    public String sortPage(@RequestParam(name = "select")Integer filter,Model model){
     model.addAttribute("books",booksService.getSortedPage(filter));
        return "library/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findById(id));
        Optional<Person> bookOwner = Optional.ofNullable(booksService.findById(id).getOwner());
        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }

        return "library/show";
    }

    @GetMapping("/new")
    public String pageForCreateNewBook(@ModelAttribute("book") Book book) {
        return "library/new";
    }

    @PostMapping()
    public String addNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "library/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "library/edit";
    }

    @PatchMapping("/edit/{id}")
    public String edit(@ModelAttribute("book") @Valid Book book, @PathVariable int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "library/edit";
        }
        booksService.update(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String deletePerson(@PathVariable int id) {
        booksService.deleteOwner(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/set")
    public String setPerson(@PathVariable int id, @ModelAttribute("person") Person person) {
        booksService.addOwner(id, person);
        return "redirect:/books";

    }


    @GetMapping("/search")
    public String getSearchPage() {
        return "library/search";
    }
    @PostMapping("/search")
    public String getResultOfSearching(Model model, @RequestParam(name = "keyword") String keyword) {
        model.addAttribute("result", booksService.findByNameStartingWith(keyword));
        return "library/search";
    }



}
