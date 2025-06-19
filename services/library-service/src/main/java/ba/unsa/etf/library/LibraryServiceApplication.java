package ba.unsa.etf.library;

import ba.unsa.etf.library.dao.model.LibraryEntity;
import ba.unsa.etf.library.dao.model.RoleEntity;
import ba.unsa.etf.library.dao.model.UserEntity;
import ba.unsa.etf.library.dao.repository.LibraryRepository;
import ba.unsa.etf.library.dao.repository.RoleRepository;
import ba.unsa.etf.library.dao.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication(scanBasePackages = {
		"ba.unsa.etf.library",
		"ba.unsa.etf.security_core.jwt"
})
@AllArgsConstructor
public class LibraryServiceApplication implements CommandLineRunner {

	private LibraryRepository libraryRepository;

	private RoleRepository roleRepository;

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {

		LibraryEntity downtown = new LibraryEntity(null, "Downtown Library", "5th Avenue", "555-1010");
		LibraryEntity uptown = new LibraryEntity(null, "Uptown Branch", "Main Street 42", "555-2020");
		LibraryEntity riverside = new LibraryEntity(null, "Riverside Library", "River Road 11", "555-3030");
		LibraryEntity westend = new LibraryEntity(null, "West End Library", "Sunset Blvd 88", "555-4040");
		LibraryEntity greenfield = new LibraryEntity(null, "Greenfield Branch", "Green St 19", "555-5050");
		libraryRepository.saveAll(List.of(downtown, uptown, riverside, westend, greenfield));

		RoleEntity roleSuperAdmin = new RoleEntity(null, "SUPERADMIN");
		RoleEntity roleAdmin = new RoleEntity(null, "ADMIN");
		RoleEntity roleLibrarian = new RoleEntity(null, "LIBRARIAN");
		RoleEntity roleUser = new RoleEntity(null, "USER");
		roleRepository.saveAll(List.of(roleSuperAdmin, roleAdmin, roleLibrarian, roleUser));

		String passUser = passwordEncoder.encode("User123*//*");
		String passAdmin = passwordEncoder.encode("Admin123*//*");
		String passLib = passwordEncoder.encode("Librarian123*//*");
		String passSuper = passwordEncoder.encode("Superadmin123*//*");

		userRepository.saveAll(List.of(
				new UserEntity(null, "Emma", "Johnson", "emma.johnson@gmail.com", passUser, "555-1111", roleUser, downtown),
				new UserEntity(null, "Liam", "Williams", "liam.williams@gmail.com", passUser, "555-1112", roleUser, uptown),
				new UserEntity(null, "Olivia", "Brown", "olivia.brown@gmail.com", passUser, "555-1113", roleUser, riverside),
				new UserEntity(null, "Noah", "Jones", "noah.jones@gmail.com", passUser, "555-1114", roleUser, westend),
				new UserEntity(null, "Ava", "Garcia", "ava.garcia@gmail.com", passUser, "555-1115", roleUser, greenfield),
				new UserEntity(null, "William", "Martinez", "will.martinez@gmail.com", passUser, "555-1116", roleUser, downtown),
				new UserEntity(null, "Sophia", "Lopez", "sophia.lopez@gmail.com", passUser, "555-1117", roleUser, uptown),
				new UserEntity(null, "James", "Gonzalez", "james.gonzalez@gmail.com", passUser, "555-1118", roleUser, riverside)
		));
		userRepository.saveAll(List.of(
				new UserEntity(null, "Lucas", "Clark", "librarian1@gmail.com", passLib, "555-2221", roleLibrarian, downtown),
				new UserEntity(null, "Mia", "Lewis", "librarian2@gmail.com", passLib, "555-2222", roleLibrarian, greenfield)
		));
		userRepository.saveAll(List.of(
				new UserEntity(null, "Benjamin", "Hall", "admin1@gmail.com", passAdmin, "555-3331", roleAdmin, westend),
				new UserEntity(null, "Isabella", "Allen", "admin2@gmail.com", passAdmin, "555-3332", roleAdmin, riverside)
		));
		userRepository.save(new UserEntity(null, "Charlotte", "King", "superadmin@gmail.com", passSuper, "555-9999", roleSuperAdmin, null));
	}
}
