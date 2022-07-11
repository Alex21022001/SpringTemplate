package spring.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spring.library.models.Book;
import spring.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, surname, year) VALUES(?,?,?)",person.getName(),person.getSurname(),person.getYear());
    }

    public void changePerson(Person person, int id) {
        jdbcTemplate.update("UPDATE Person SET name=?,surname=?,year=? WHERE id=?",person.getName(),person.getSurname(),person.getYear(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?",id);
    }

    public Optional<Book> showSimilarBook(String title) {
        return jdbcTemplate.query("SELECT * from library WHERE title=?",new Object[]{title},new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }


//    insert into person(name, surname, year) VALUES ('Stas','Bondar',2000)
}
