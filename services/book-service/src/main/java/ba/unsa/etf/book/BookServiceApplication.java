package ba.unsa.etf.book;

import ba.unsa.etf.book.dao.model.*;
import ba.unsa.etf.book.dao.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootApplication(scanBasePackages = {
		"ba.unsa.etf.book",
		"ba.unsa.etf.security_core.jwt"
})
@AllArgsConstructor
public class BookServiceApplication implements CommandLineRunner {

	private AuthorRepository authorRepository;

	private GenreRepository genreRepository;

	private BookRepository bookRepository;

	private BookVersionRepository bookVersionRepository;

	private LoanRepository loanRepository;

	private ReservationRepository reservationRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		AuthorEntity rowling = new AuthorEntity(null, "J.K.", "Rowling", "Author of the Harry Potter fantasy series.");
		AuthorEntity tolkien = new AuthorEntity(null, "J.R.R.", "Tolkien", "Known for The Lord of the Rings and The Hobbit.");
		AuthorEntity orwell = new AuthorEntity(null, "George", "Orwell", "Famous for dystopian novels like 1984 and Animal Farm.");
		AuthorEntity austen = new AuthorEntity(null, "Jane", "Austen", "Known for romantic fiction including Pride and Prejudice.");
		AuthorEntity king = new AuthorEntity(null, "Stephen", "King", "Master of horror and suspense.");

		authorRepository.saveAll(List.of(rowling, tolkien, orwell, austen, king));

		GenreEntity fantasy = new GenreEntity(null, "Fantasy");
		GenreEntity dystopian = new GenreEntity(null, "Dystopian");
		GenreEntity romance = new GenreEntity(null, "Romance");
		GenreEntity horror = new GenreEntity(null, "Horror");
		GenreEntity classic = new GenreEntity(null, "Classic");

		genreRepository.saveAll(List.of(fantasy, dystopian, romance, horror, classic));

		BookEntity hp = new BookEntity(null, "Harry Potter and the Sorcerer's Stone", "A young wizard's journey begins.", 320, 1997, "English", rowling, fantasy);
		BookEntity lotr = new BookEntity(null, "The Fellowship of the Ring", "The first part of The Lord of the Rings trilogy.", 423, 1954, "English", tolkien, fantasy);
		BookEntity animalFarm = new BookEntity(null, "Animal Farm", "A satirical allegory about totalitarianism.", 112, 1945, "English", orwell, dystopian);
		BookEntity pride = new BookEntity(null, "Pride and Prejudice", "A classic romance novel.", 279, 1813, "English", austen, romance);
		BookEntity shining = new BookEntity(null, "The Shining", "A psychological horror novel.", 447, 1977, "English", king, horror);

		bookRepository.saveAll(List.of(hp, lotr, animalFarm, pride, shining));

		List<Long> libraryIds = List.of(1L, 2L, 3L, 4L, 5L);
		List<BookEntity> books = List.of(hp, lotr, animalFarm, pride, shining);
		int isbnCounter = 2000;

		Random rand = new Random();
		List<Long> userIds = List.of(1L,2L,3L,4L,5L);

		for (BookEntity book : books) {
			for (Long libraryId : libraryIds) {
				for (int i = 0; i < 6; i++) {
					String isbn = "9781234" + (isbnCounter++);
					boolean isCheckedOut = (i == 0 || i == 1);
					boolean isReserved = (i == 2 || i == 3);

					BookVersionEntity version = new BookVersionEntity(isbn, book, isCheckedOut, isReserved, libraryId);
					bookVersionRepository.save(version);

					Long userId = userIds.get(rand.nextInt(userIds.size()));
					LocalDate today = LocalDate.now();

					if (isCheckedOut) {
						LocalDate loanDate = today.minusDays(rand.nextInt(30));
						LocalDate dueDate = loanDate.plusDays(14 + rand.nextInt(15));
						LoanEntity loan = new LoanEntity(null, userId, version, loanDate, dueDate, null);
						loanRepository.save(loan);
					}
					if (isReserved) {
						LocalDate reservationDate = today.minusDays(rand.nextInt(20));
						ReservationEntity reservation = new ReservationEntity(null, userId, version, reservationDate);
						reservationRepository.save(reservation);
					}
				}
			}
		}
	}
}
