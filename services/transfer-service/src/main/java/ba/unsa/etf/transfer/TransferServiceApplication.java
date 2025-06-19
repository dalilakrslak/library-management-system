package ba.unsa.etf.transfer;

import ba.unsa.etf.transfer.dao.model.*;
import ba.unsa.etf.transfer.dao.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication(scanBasePackages = {
		"ba.unsa.etf.transfer",
		"ba.unsa.etf.security_core.jwt"
})
@AllArgsConstructor
public class TransferServiceApplication  implements CommandLineRunner {

	private TransferRepository transferRepository;

	public static void main(String[] args) {
		SpringApplication.run(TransferServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		List<TransferEntity> transfers = List.of(
				new TransferEntity(null, "9780061120084", 1L, 2L, null), // Ongoing
				new TransferEntity(null, "9780451524935", 2L, 3L, LocalDate.of(2025, 6, 15)),
				new TransferEntity(null, "9780743273565", 3L, 1L, LocalDate.of(2025, 5, 30)),
				new TransferEntity(null, "9780547928227", 2L, 4L, null), // Ongoing
				new TransferEntity(null, "9780142437209", 4L, 5L, LocalDate.of(2025, 6, 10)),
				new TransferEntity(null, "9780385504201", 5L, 1L, LocalDate.of(2025, 6, 5)),
				new TransferEntity(null, "9780307277671", 1L, 3L, null)  // Ongoing
		);

		transferRepository.saveAll(transfers);
	}
}
