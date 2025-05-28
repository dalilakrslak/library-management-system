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

	private TransferRepository transferRepository;

	public static void main(String[] args) {
		SpringApplication.run(TransferServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		TransferEntity transfer = new TransferEntity(null, "97812341002", 1L, 2L, null);
		transferRepository.save(transfer);

		TransferEntity transfer2 = new TransferEntity(null, "97812341005", 2L, 3L, LocalDate.now());
		transferRepository.save(transfer2);
	}
}
