package ba.unsa.etf.transfer;

import ba.unsa.etf.transfer.dao.model.*;
import ba.unsa.etf.transfer.dao.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
@AllArgsConstructor
public class TransferServiceApplication  implements CommandLineRunner {
	private AuthorRepository authorRepository;

	private BookRepository bookRepository;

	private BookVersionRepository bookVersionRepository;

	private GenreRepository genreRepository;

	private LibraryRepository libraryRepository;

	private TransferRepository transferRepository;

	public static void main(String[] args) {
		SpringApplication.run(TransferServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		AuthorEntity author = new AuthorEntity(null, "Ivo", "Andrić", "Dobitnik Nobelove nagrade za književnost.");
		authorRepository.save(author);

		GenreEntity genre = new GenreEntity(null, "Roman");
		genreRepository.save(genre);

		BookEntity book = new BookEntity(null, "Na Drini ćuprija", "Istorijski roman o viševekovnoj istoriji mosta.", 314, "1945", "Srpski", author, genre);
		bookRepository.save(book);

		LibraryEntity library1 = new LibraryEntity(null, "Nacionalna biblioteka", "Sarajevo, BiH", "+387 33 123 456");
		LibraryEntity library2 = new LibraryEntity(null, "Gradska biblioteka", "Mostar, BiH", "+387 36 654 321");
		libraryRepository.save(library1);
		libraryRepository.save(library2);

		BookVersionEntity bookVersion = new BookVersionEntity("978-86-123-4567-8", book, library1, false, false);
		bookVersionRepository.save(bookVersion);

		TransferEntity transfer = new TransferEntity(null, bookVersion, library1, library2, LocalDate.now());
		transferRepository.save(transfer);
	}
}
