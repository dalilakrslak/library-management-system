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

@SpringBootApplication
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

		LibraryEntity centar = new LibraryEntity(null, "Centar", "Alipašina", "061234567");
		LibraryEntity grbavica = new LibraryEntity(null, "Grbavica", "Grbavička 20", "061111111");
		LibraryEntity dobrinja = new LibraryEntity(null, "Dobrinja", "Šefika Džaferovića 7", "062222222");
		libraryRepository.save(centar);
		libraryRepository.save(grbavica);
		libraryRepository.save(dobrinja);

		RoleEntity roleUser = new RoleEntity(null,"USER");
		RoleEntity roleAdmin = new RoleEntity(null,"ADMIN");
		roleRepository.save(roleUser);
		roleRepository.save(roleAdmin);

		String encodedUserPass = passwordEncoder.encode("lozinka");
		UserEntity user = new UserEntity(null, "Marko", "Marić", "mmaric1@gmail.com", encodedUserPass, "062361598", roleUser, centar);
		userRepository.save(user);

		String encodedAdminPass = passwordEncoder.encode("admin123");
		UserEntity admin = new UserEntity(null, "Admin", "Adminović", "admin@gmail.com", encodedAdminPass, "060123456", roleAdmin, centar);
		userRepository.save(admin);
	}

}
