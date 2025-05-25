package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanService {
    List<Loan> findAll();

    Loan findById(Long id);

    Loan create(Loan loan);

    Loan update(Loan loan);

    void delete(Long id);

    Page<Loan> getAllLoans(Pageable pageable);

    List<Loan> createBatch(List<Loan> loans);

    List<Loan> findLoansByUserId(Long userId);
}
