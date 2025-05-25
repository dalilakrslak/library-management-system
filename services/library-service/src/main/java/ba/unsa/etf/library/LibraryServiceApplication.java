package ba.unsa.etf.library;

import ba.unsa.etf.library.dao.model.BookVersionEntity;
import ba.unsa.etf.library.dao.model.LibraryEntity;
import ba.unsa.etf.library.dao.model.RoleEntity;
import ba.unsa.etf.library.dao.model.UserEntity;
import ba.unsa.etf.library.dao.repository.BookVersionRepository;
import ba.unsa.etf.library.dao.repository.LibraryRepository;
import ba.unsa.etf.library.dao.repository.RoleRepository;
import ba.unsa.etf.library.dao.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@AllArgsConstructor
public class LibraryServiceApplication implements CommandLineRunner {
	private BookVersionRepository bookVersionRepository;

	private LibraryRepository libraryRepository;

	private RoleRepository roleRepository;

	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {

		LibraryEntity library = new LibraryEntity(null, "Centar", "Alipašina", "061234567");
		libraryRepository.save(library);

		BookVersionEntity bookVersion = new BookVersionEntity("9781234567897", library, false, false);
		bookVersionRepository.save(bookVersion);

		RoleEntity role = new RoleEntity(null,"USER");
		roleRepository.save(role);

		UserEntity user = new UserEntity(null, "Marko", "Marić", "mmaric1@gmail.com", "lozinka", "062361598", role, library);
		userRepository.save(user);
	}

}
