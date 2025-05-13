package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Loan;
import ba.unsa.etf.book.api.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    private Loan loan;
    private List<Loan> loans;

    @BeforeEach
    void setUp() {
        loan = new Loan(
                1L,
                100L,
                "978-99955-42-10-4",
                LocalDateTime.of(2024, 5, 1, 10, 0),
                LocalDateTime.of(2024, 6, 1, 10, 0),
                null
        );

        loans = List.of(loan);
    }

    @Test
    void findAll_ShouldReturnListOfLoans() throws Exception {
        when(loanService.findAll()).thenReturn(loans);

        mockMvc.perform(get("/loan"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value(100));
    }

    @Test
    void findById_ShouldReturnLoan() throws Exception {
        when(loanService.findById(1L)).thenReturn(loan);

        mockMvc.perform(get("/loan/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookVersion").value("978-99955-42-10-4"));
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() throws Exception {
        doNothing().when(loanService).delete(1L);

        mockMvc.perform(delete("/loan/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Loan with ID 1 was deleted successfully."));
    }

    @Test
    void findLoansByUserId_ShouldReturnUserLoans() throws Exception {
        when(loanService.findLoansByUserId(100L)).thenReturn(loans);

        mockMvc.perform(get("/loan/search")
                        .param("userId", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookVersion").value("978-99955-42-10-4"));
    }

    @Test
    void findAllPaginated_ShouldReturnPagedLoans() throws Exception {
        Page<Loan> page = new PageImpl<>(loans);
        when(loanService.getAllLoans(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/loan/paginated")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "loanDate", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].userId").value(100));
    }

    @Test
    void create_ShouldReturnCreatedLoan() throws Exception {
        when(loanService.create(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(post("/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookVersion").value("978-99955-42-10-4"));
    }

    @Test
    void update_ShouldReturnUpdatedLoan() throws Exception {
        loan.setReturnDate(LocalDateTime.of(2024, 5, 15, 10, 0));
        when(loanService.update(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(put("/loan/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnDate").value("2024-05-15T10:00:00"));
    }

    @Test
    void createBatch_ShouldReturnCreatedLoans() throws Exception {
        when(loanService.createBatch(anyList())).thenReturn(loans);

        mockMvc.perform(post("/loan/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loans)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(100));
    }
}
