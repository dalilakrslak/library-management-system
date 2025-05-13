package ba.unsa.etf.book.repository;

import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.model.ReservationEntity;
import ba.unsa.etf.book.dao.model.UserEntity;
import ba.unsa.etf.book.dao.repository.ReservationRepository;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationTests {

    @Autowired
    private ReservationRepository reservationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Statistics statistics;

    @Test
    void whenFetchingReservationsByUserId_NoNPlusOneProblem() {
        Session session = entityManager.unwrap(Session.class);
        Statistics stats = session.getSessionFactory().getStatistics();
        stats.setStatisticsEnabled(true);
        stats.clear();

        // Setup data
        UserEntity user = new UserEntity();
        user.setFirstName("John");
        user.setLastName("Doe");
        entityManager.persist(user);

        BookVersionEntity bookVersion = new BookVersionEntity();
        bookVersion.setIsbn("12345");
        bookVersion.setIsCheckedOut(false);
        bookVersion.setIsReserved(true);
        entityManager.persist(bookVersion);

        for (int i = 0; i < 3; i++) {
            ReservationEntity reservation = new ReservationEntity();
            reservation.setUser(user);
            reservation.setBookVersion(bookVersion);
            reservation.setReservationDate(LocalDate.now());
            entityManager.persist(reservation);
        }

        entityManager.flush();
        entityManager.clear(); // Clear persistence context to simulate fresh fetch

        // ACT
        List<ReservationEntity> reservations = reservationRepository.findReservationsByUserId(user.getId());

        // Trigger lazy loading if fetch wasn't eager (simulate potential N+1)
        for (ReservationEntity r : reservations) {
            r.getUser().getFirstName();         // triggers lazy load if not eager
            r.getBookVersion().getIsbn();       // same here
        }

        long queryCount = stats.getQueryExecutionCount();
        System.out.println("Executed queries: " + queryCount);

        // ASSERT
        assertEquals(1, queryCount, "There should be only one query executed!");
    }

}
