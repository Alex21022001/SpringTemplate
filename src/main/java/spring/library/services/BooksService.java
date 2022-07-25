package spring.library.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.library.models.Book;
import spring.library.models.Person;
import spring.library.repositories.BooksRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final EntityManager entityManager;

    @Autowired
    public BooksService(BooksRepository booksRepository, EntityManager entityManager) {
        this.booksRepository = booksRepository;
        this.entityManager = entityManager;
    }

    public List<Book> findAll(boolean sorted) {
        if (sorted)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }

    public Book findById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> findFreeBook() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT b FROM Book b WHERE b.owner.id=null ").getResultList();
    }


    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void addOwner(int id, Person person) {
        Session session = entityManager.unwrap(Session.class);
        session.get(Book.class, id).setOwner(person);
    }


    @Transactional
    public void deleteOwner(int id) {
        booksRepository.findById(id).orElse(null).setOwner(null);
    }

    public List<Book> getPageWithParam(Integer page, Integer booksPerPage, boolean sorted) {
        if (sorted)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findByNameStartingWith(String text) {
        List<Book> result = new ArrayList<>();
        List<Book> books = findAll(false);
        if (text == "") {
            return Collections.emptyList();
        } else {
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getTitle().toLowerCase().contains(text)) {
                    result.add(books.get(i));
                }
            }
            return result;
        }
    }

    public List<Book> getSortedPage(Integer filter) {
        if (filter==1) {
            return booksRepository.findAll(Sort.by("title"));
        } else if (filter==2) {
            return booksRepository.findAll(Sort.by("year"));
        }
        return booksRepository.findAll();
    }
}
