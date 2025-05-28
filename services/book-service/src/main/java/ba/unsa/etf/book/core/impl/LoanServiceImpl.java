package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.*;
import ba.unsa.etf.book.api.service.LoanService;
import ba.unsa.etf.book.core.mapper.LoanMapper;
import ba.unsa.etf.book.core.validation.LoanValidation;
import ba.unsa.etf.book.dao.model.LoanEntity;
import ba.unsa.etf.book.dao.model.ReservationEntity;
import ba.unsa.etf.book.dao.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanValidation loanValidation;
    private final RestTemplate restTemplate;

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

    @Override
    public Page<Loan> getAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable).map(loanMapper::entityToDto);
    }

    @Override
    public List<Loan> createBatch(List<Loan> loans) {
        List<LoanEntity> entities = loans.stream().map(loanMapper::dtoToEntity).collect(Collectors.toList());
        entities.addAll(loanRepository.saveAll(entities));
        return entities.stream().map(loanMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Loan> findLoansByUserId(Long userId) {
        List<LoanEntity> entities = loanRepository.findByUserId(userId);
        return entities.stream().map(loanMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<LoanWithUser> getAllLoansWithUserInfo() {
        List<LoanEntity> entities = loanRepository.findAll();

        return entities.stream().map(entity -> {
            String url = "http://library-service/user/" + entity.getUserId();
            User user = restTemplate.getForObject(url, User.class);

            Loan loan = loanMapper.entityToDto(entity);
            return new LoanWithUser(loan, user);
        }).collect(Collectors.toList());
    }

    @Override
    public User getUserByLoanId(Long loanId) {
        Loan loan = loanRepository.findById(loanId).map(loanMapper::entityToDto).orElse(null);
        String url = "http://library-service/user/" + loan.getUserId();

        return restTemplate.getForObject(url, User.class);
    }
}
