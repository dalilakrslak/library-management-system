package ba.unsa.etf.book.service;

import ba.unsa.etf.book.api.model.Loan;
import ba.unsa.etf.book.core.impl.LoanServiceImpl;
import ba.unsa.etf.book.core.mapper.LoanMapper;
import ba.unsa.etf.book.core.validation.LoanValidation;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.model.LoanEntity;
import ba.unsa.etf.book.dao.model.UserEntity;
import ba.unsa.etf.book.dao.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanTests {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    @Mock
    private LoanValidation loanValidation;

    @InjectMocks
    private LoanServiceImpl loanService;

    private LoanEntity loanEntity;
    private Loan loanDto;
    private BookEntity bookEntity;
    private BookVersionEntity bookVersionEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        LocalDate now = LocalDate.now();
        bookEntity = new BookEntity(1L, "Title", "Description", 300, 2022, "English", null, null);
        bookVersionEntity = new BookVersionEntity("123-456-789", bookEntity, false, false);
        userEntity = new UserEntity(1L, "Joe", "Smith", "joe.smith@gmail.com", "password", "060123456");
        loanEntity = new LoanEntity(1L, userEntity, bookVersionEntity, now, now.plusDays(14), null);
        loanDto = new Loan(1L, 1L, "1234567890", LocalDateTime.now(), LocalDateTime.now().plusDays(14), null);
    }

    @Test
    void testFindAll() {
        List<LoanEntity> loanEntities = List.of(loanEntity);
        List<Loan> loanDtos = List.of(loanDto);

        when(loanRepository.findAll()).thenReturn(loanEntities);
        when(loanMapper.entityToDto(loanEntities.get(0))).thenReturn(loanDtos.get(0));

        List<Loan> result = loanService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(loanDtos.get(0).getId(), result.get(0).getId());

        verify(loanRepository).findAll();
        verify(loanMapper).entityToDto(any(LoanEntity.class));
    }

    @Test
    void testFindById_LoanExists() {
        Long id = 1L;
        when(loanRepository.findById(id)).thenReturn(Optional.of(loanEntity));
        when(loanMapper.entityToDto(loanEntity)).thenReturn(loanDto);

        Loan result = loanService.findById(id);

        assertNotNull(result);
        assertEquals(loanDto.getId(), result.getId());

        verify(loanValidation).exists(id);
        verify(loanRepository).findById(id);
        verify(loanMapper).entityToDto(loanEntity);
    }

    @Test
    void testFindById_LoanNotFound() {
        Long id = 2L;
        when(loanRepository.findById(id)).thenReturn(Optional.empty());

        Loan result = loanService.findById(id);

        assertNull(result);
        verify(loanRepository).findById(id);
        verifyNoMoreInteractions(loanMapper);
    }

    @Test
    void testCreate() {
        bookEntity = new BookEntity(1L, "Title", "Description", 300, 2022, "English", null, null);
        bookVersionEntity = new BookVersionEntity("123-456-789", bookEntity, false, false);
        userEntity = new UserEntity(1L, "Joe", "Smith", "joe.smith@gmail.com", "password", "060123456");
        Loan loanToCreate = new Loan(1L, 1L, "1234567890", LocalDateTime.now(), LocalDateTime.now().plusDays(14), null);
        LoanEntity loanEntity = new LoanEntity(1L, userEntity, bookVersionEntity, LocalDate.now(), LocalDate.now().plusDays(14), null);
        LoanEntity savedEntity = new LoanEntity(1L, userEntity, bookVersionEntity, LocalDate.now(), LocalDate.now().plusDays(14), null);
        Loan loanDto = new Loan(1L, 1L, "1234567890", LocalDateTime.now(), LocalDateTime.now().plusDays(14), null);

        when(loanMapper.dtoToEntity(loanToCreate)).thenReturn(loanEntity);
        when(loanRepository.save(loanEntity)).thenReturn(savedEntity);
        when(loanMapper.entityToDto(savedEntity)).thenReturn(loanDto);

        Loan result = loanService.create(loanToCreate);

        assertNotNull(result);
        assertEquals(loanDto.getId(), result.getId());

        verify(loanRepository).save(loanEntity);
        verify(loanMapper).dtoToEntity(loanToCreate);
        verify(loanMapper).entityToDto(savedEntity);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(loanRepository).deleteById(id);

        loanService.delete(id);

        verify(loanRepository).deleteById(id);
    }
}
