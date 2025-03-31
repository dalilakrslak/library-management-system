package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.Loan;
import ba.unsa.etf.book.api.service.LoanService;
import ba.unsa.etf.book.core.mapper.LoanMapper;
import ba.unsa.etf.book.core.validation.LoanValidation;
import ba.unsa.etf.book.dao.model.LoanEntity;
import ba.unsa.etf.book.dao.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanValidation loanValidation;

    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Loan findById(Long id) {
        loanValidation.exists(id);
        return loanRepository.findById(id).map(loanMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Loan create(Loan loan) {
        LoanEntity loanEntity = loanMapper.dtoToEntity(loan);
        loanRepository.save(loanEntity);
        return loanMapper.entityToDto(loanEntity);
    }

    @Override
    @Transactional
    public Loan update(Loan loan) {
        LoanEntity loanEntity = loanMapper.dtoToEntity(loan);
        loanRepository.save(loanEntity);
        return loanMapper.entityToDto(loanEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        loanValidation.exists(id);
        loanRepository.deleteById(id);
    }
}
