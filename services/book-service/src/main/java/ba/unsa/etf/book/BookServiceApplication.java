package ba.unsa.etf.book;

import ba.unsa.etf.book.dao.model.*;
import ba.unsa.etf.book.dao.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
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
		AuthorEntity author = new AuthorEntity(null, "Ivo", "Andrić", "Dobitnik Nobelove nagrade za književnost.");
		authorRepository.save(author);

		GenreEntity genre = new GenreEntity(null, "Roman");
		genreRepository.save(genre);

		BookEntity book = new BookEntity(null, "Na Drini ćuprija", "Roman o životu u Bosni pod Osmanlijama.",312, 1945, "bosanski", author, genre);
		bookRepository.save(book);

		List<Long> libraryIds = List.of(1L, 2L, 3L);
		int isbnCounter = 1000;

		for (Long libraryId : libraryIds) {
			for (int i = 0; i < 3; i++) {
				String isbn = "9781234" + (isbnCounter++);
				boolean isCheckedOut = (i == 0);
				boolean isReserved = (i == 1);
				BookVersionEntity version = new BookVersionEntity(isbn, book, isCheckedOut, isReserved, libraryId);
				bookVersionRepository.save(version);

				if (isCheckedOut) {
					LoanEntity loan = new LoanEntity(null, 1L, version,
							LocalDate.of(2025, 3, 23),
							LocalDate.of(2025, 4, 6),
							null);
					loanRepository.save(loan);
				}
				if (isReserved) {
					ReservationEntity reservation = new ReservationEntity(null, 1L, version, LocalDate.now());
					reservationRepository.save(reservation);
				}
			}
		}
	}
}
