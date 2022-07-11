package spring.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.library.dao.PersonDAO;
import spring.library.models.Book;

@Component
public class BookValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public BookValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (personDAO.showSimilarBook(book.getTitle()).isPresent()){
            errors.rejectValue("title","","This Book already exists");
        }
    }
}
