package spring.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.library.models.Book;
import spring.library.models.Person;
import spring.library.repositories.BooksRepository;
import spring.library.repositories.PeopleRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final EntityManager entityManager;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntityManager entityManager, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.entityManager = entityManager;
        this.booksRepository = booksRepository;
    }


    public List<Person> findAll() {
//        return peopleRepository.findAll(PageRequest.of(1,4,Sort.by("name"))).getContent();
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
       return peopleRepository.findById(id).orElse(null);
    }


    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void addOwner(int id, Book book) {
        booksRepository.findById(book.getId()).orElse(null).setOwner(peopleRepository.findById(id).orElse(null));
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }


    public List<Person> findByName(String name) {
        return peopleRepository.findByName(name);
    }

    public List<Person> findByNameStartingWith(String startingName) {
        return peopleRepository.findByNameStartingWith(startingName);
    }


}
