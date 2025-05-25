package ba.unsa.etf.book.repository;

import ba.unsa.etf.book.dao.model.AuthorEntity;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.model.GenreEntity;
import ba.unsa.etf.book.dao.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookTests {

    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Statistics statistics;

    @BeforeEach
    void setUp() {
        SessionFactory sessionFactory = entityManager.unwrap(Session.class).getSessionFactory();
        statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);

        AuthorEntity author = new AuthorEntity();
        author.setFirstName("Ivo");
        author.setLastName("Andrić");

        GenreEntity genre = new GenreEntity();
        genre.setName("Roman");

        entityManager.persist(author);
        entityManager.persist(genre);

        for (int i = 0; i < 3; i++) {
            BookEntity book = new BookEntity();
            book.setTitle("Na Drini ćuprija " + i);
            book.setDescription("Opis knjige " + i);
            book.setPageCount(300 + i);
            book.setPublicationYear(1945 + i);
            book.setLanguage("Bosnian");
            book.setAuthor(author);
            book.setGenre(genre);
            entityManager.persist(book);
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void whenFetchingBooksByAuthor_NoNPlusOneProblem() {
        List<BookEntity> books = bookRepository.findBooksByAuthor("Ivo");

        long queryCount = statistics.getQueryExecutionCount();

        assertEquals(1, queryCount, "There should be only one query executed!");
    }

}
