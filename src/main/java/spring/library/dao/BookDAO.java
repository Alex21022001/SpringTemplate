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
public class BookDAO  {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Library",new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM library WHERE id=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class))
                        .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO library(title, author, year) VALUES (?,?,?)",book.getTitle(),book.getAuthor(),book.getYear());
    }

    public void update(Book book, int id) {
        jdbcTemplate.update("UPDATE library SET title=?,author=?,year=? WHERE id=?",book.getTitle(),book.getAuthor(),book.getYear(),id);
    }


    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM library WHERE id=?",id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("UPDATE library SET person_id=null WHERE id=?",id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("select p.id,name,surname,p.year from library join person p on p.id = library.person_id WHERE library.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void addPerson(int id,Person person) {
        jdbcTemplate.update("UPDATE library SET person_id=? WHERE id=? ",person.getId(),id);
    }

    public List<Book> showListOfPersonalBook(int id) {
        return jdbcTemplate.query("SELECT * FROM library WHERE person_id=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class));
    }
}
