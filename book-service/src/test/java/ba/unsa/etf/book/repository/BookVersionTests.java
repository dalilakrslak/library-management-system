package ba.unsa.etf.book.repository;

import ba.unsa.etf.book.dao.model.*;
import ba.unsa.etf.book.dao.repository.BookVersionRepository;
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
public class BookVersionTests {

    @Autowired
    private BookVersionRepository bookVersionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Statistics statistics;

    @BeforeEach
    void setUp() {
        SessionFactory sessionFactory = entityManager.unwrap(Session.class).getSessionFactory();
        statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);

        AuthorEntity author = new AuthorEntity();
        author.setFirstName("Mesa");
        author.setLastName("Selimovic");

        GenreEntity genre = new GenreEntity();
        genre.setName("Drama");

        entityManager.persist(author);
        entityManager.persist(genre);

        for (int i = 0; i < 3; i++) {
            BookEntity book = new BookEntity();
            book.setTitle("Dervis i smrt " + i);
            book.setDescription("Opis knjige " + i);
            book.setPageCount(250 + i);
            book.setPublicationYear(1960 + i);
            book.setLanguage("Bosnian");
            book.setAuthor(author);
            book.setGenre(genre);
            entityManager.persist(book);

            BookVersionEntity version = new BookVersionEntity();
            version.setIsbn("ISBN-" + i);
            version.setBook(book);
            version.setIsCheckedOut(false);
            version.setIsReserved(false);
            entityManager.persist(version);
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void whenFetchingBooksByTitle_NoNPlusOneProblem() {
        List<BookVersionEntity> versions = bookVersionRepository.findBooksByTitle("Dervis");

        versions.forEach(version -> {
            version.getBook().getTitle();
            version.getBook().getAuthor().getFirstName();
            version.getBook().getGenre().getName();
        });

        long queryCount = statistics.getQueryExecutionCount();

        assertEquals(1, queryCount, "There should be only one query executed!");
    }

    @Test
    void whenCallingFindAll_thenNoNPlusOneProblem() {
        List<BookVersionEntity> versions = bookVersionRepository.findAll();

        versions.forEach(version -> {
            version.getBook().getTitle();
            version.getBook().getAuthor().getLastName();
            version.getBook().getGenre().getName();
        });

        long queryCount = statistics.getQueryExecutionCount();

        assertEquals(1, queryCount, "There should be only one query executed!");
    }
}
