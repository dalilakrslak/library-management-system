package ba.unsa.etf.book;

import ba.unsa.etf.book.dao.model.*;
import ba.unsa.etf.book.dao.repository.*;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@AllArgsConstructor
public class BookServiceApplication implements CommandLineRunner {

	private AuthorRepository authorRepository;

	private GenreRepository genreRepository;

	private BookRepository bookRepository;

	private UserRepository userRepository;

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

		UserEntity user = new UserEntity(null, "Marko", "Marković", "marko@gmail.com", "1234", "061111111");
		userRepository.save(user);

		BookEntity book = new BookEntity(null, "Na Drini ćuprija", "Roman o životu u Bosni pod Osmanlijama.",312, 1945, "bosanski", author, genre);
		bookRepository.save(book);

		BookVersionEntity bookVersion = new BookVersionEntity("9781234567897", book, false, false);
		bookVersionRepository.save(bookVersion);

		LoanEntity loan = new LoanEntity(null, user, bookVersion,
				LocalDate.of(2025, 3, 23),
				LocalDate.of(2025, 4, 6),
				null);
		loanRepository.save(loan);

		ReservationEntity reservation = new ReservationEntity(null, user, bookVersion, LocalDate.now());
		reservationRepository.save(reservation);
	}
}
