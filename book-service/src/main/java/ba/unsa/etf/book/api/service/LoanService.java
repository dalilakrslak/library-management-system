package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> findAll();

    Loan findById(Long id);

    Loan create(Loan loan);

    Loan update(Loan loan);

    void delete(Long id);
}
