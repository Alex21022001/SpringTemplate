package spring.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.library.models.Book;
import spring.library.models.Person;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {

    List<Person> findByName(String name);

    List<Person> findByNameStartingWith(String nameStartingWith);

}
