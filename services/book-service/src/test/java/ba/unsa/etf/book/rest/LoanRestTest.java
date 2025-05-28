package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Loan;
import ba.unsa.etf.book.api.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LoanRestTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanRest loanRest;

    private Loan loan;
    private List<Loan> loans;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loan = new Loan(
                1L,
                100L,
                "978-99955-42-10-4",
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 1),
                null
        );
        loans = List.of(loan);
    }

    @Test
    void findAll_ShouldReturnListOfLoans() {
        when(loanService.findAll()).thenReturn(loans);
        List<Loan> result = loanRest.findAll();
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserId());
    }

    @Test
    void findById_ShouldReturnLoan() {
        when(loanService.findById(1L)).thenReturn(loan);
        Loan result = loanRest.findById(1L).getBody();
        assertNotNull(result);
        assertEquals("978-99955-42-10-4", result.getBookVersion());
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() {
        doNothing().when(loanService).delete(1L);
        var response = loanRest.delete(1L);
        verify(loanService).delete(1L);
        assertEquals("Loan with ID 1 was deleted successfully.", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findLoansByUserId_ShouldReturnUserLoans() {
        when(loanService.findLoansByUserId(100L)).thenReturn(loans);
        ResponseEntity<List<Loan>> result = loanRest.findLoansByUserId(100L);
        assertEquals(1, result.getBody().size());
        assertEquals("978-99955-42-10-4", result.getBody().get(0).getBookVersion());
    }

    @Test
    void findAllPaginated_ShouldReturnPagedLoans() {
        Page<Loan> page = new PageImpl<>(loans);
        when(loanService.getAllLoans(any(PageRequest.class))).thenReturn(page);

        ResponseEntity<Page<Loan>> response = loanRest.findAllPaginated(0, 10, new String[]{"loanDate", "asc"});
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(100L, response.getBody().getContent().get(0).getUserId());
    }

    @Test
    void create_ShouldReturnCreatedLoan() {
        when(loanService.create(any(Loan.class))).thenReturn(loan);
        Loan result = loanRest.create(loan).getBody();
        assertNotNull(result);
        assertEquals("978-99955-42-10-4", result.getBookVersion());
    }

    @Test
    void update_ShouldReturnUpdatedLoan() {
        loan.setReturnDate(LocalDate.of(2024, 5, 15));
        when(loanService.update(any(Loan.class))).thenReturn(loan);
        Loan result = loanRest.update(1L, loan).getBody();
        assertNotNull(result);
        assertEquals("2024-05-15", result.getReturnDate().toString());
    }

    @Test
    void createBatch_ShouldReturnCreatedLoans() {
        when(loanService.createBatch(anyList())).thenReturn(loans);
        List<Loan> result = loanRest.createBatch(loans).getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserId());
    }
}
