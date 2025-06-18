package ba.unsa.etf.book.repository;

import ba.unsa.etf.book.dao.model.*;
import ba.unsa.etf.book.dao.repository.BookVersionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class BookVersionTests {

    @Autowired
    private BookVersionRepository bookVersionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Statistics statistics;

    @Test
    void whenFetchingBooksByTitle_NoNPlusOneProblem() {
        SessionFactory sessionFactory = entityManager.unwrap(Session.class).getSessionFactory();
        Statistics statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);
        statistics.clear();

        AuthorEntity author = new AuthorEntity();
        author.setFirstName("Mesa");
        author.setLastName("Selimovic");
        entityManager.persist(author);

        GenreEntity genre = new GenreEntity();
        genre.setName("Drama");
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

        List<BookVersionEntity> versions = bookVersionRepository.findBooksByTitle("Dervis");

        versions.forEach(version -> {
            version.getBook().getTitle();
            version.getBook().getAuthor().getFirstName();
            version.getBook().getGenre().getName();
        });

        long queryCount = statistics.getQueryExecutionCount();
        System.out.println("Executed queries: " + queryCount);

        assertEquals(1, queryCount, "There should be only one query executed!");
    }

}
