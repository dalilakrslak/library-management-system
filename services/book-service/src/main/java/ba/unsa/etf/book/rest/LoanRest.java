package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Loan;
import ba.unsa.etf.book.api.model.LoanWithUser;
import ba.unsa.etf.book.api.model.ReservationWithUser;
import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.api.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "loan", description = "Loan API")
@RestController
@RequestMapping(value = "/loan")
@AllArgsConstructor
public class LoanRest {
    private LoanService loanService;

    @Operation(summary = "Find loans")
    @GetMapping
    public List<Loan> findAll() {
        return loanService.findAll();
    }

    @Operation(summary = "Find loan by ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Loan> findById(
            @Parameter(required = true, description = "ID of the loan", name = "id") @PathVariable Long id) {
        Loan loan = loanService.findById(id);
        return loan != null ? ResponseEntity.ok(loan) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create loan")
    @PostMapping
    public ResponseEntity<Loan> create(@RequestBody Loan loan) {
        Loan createdLoan = loanService.create(loan);
        return ResponseEntity.ok(createdLoan);
    }

    @Operation(summary = "Update loan")
    @PutMapping("/{id}")
    public ResponseEntity<Loan> update(@PathVariable Long id, @RequestBody Loan loan) {
        loan.setId(id);
        Loan updatedLoan = loanService.update(loan);
        return updatedLoan != null ? ResponseEntity.ok(updatedLoan) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete loan")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.ok("Loan with ID " + id + " was deleted successfully.");
    }

    @Operation(summary = "Find loans with pagination and sorting")
    @GetMapping("/paginated")
    public ResponseEntity<Page<Loan>> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "loanDate,asc") String[] sort
    ) {
        Sort sortOrder = Sort.by(
                sort[1].equalsIgnoreCase("desc") ? Sort.Order.desc(sort[0]) : Sort.Order.asc(sort[0])
        );
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Loan> loans = loanService.getAllLoans(pageable);
        return ResponseEntity.ok(loans);
    }

    @Operation(summary = "Batch create loans")
    @PostMapping("/batch")
    public ResponseEntity<List<Loan>> createBatch(@RequestBody List<Loan> loans) {
        List<Loan> createdLoans = loanService.createBatch(loans);
        return ResponseEntity.ok(createdLoans);
    }

    @Operation(summary = "Find loans by user ID")
    @GetMapping("/search")
    public ResponseEntity<List<Loan>> findLoansByUserId(
            @Parameter(description = "ID of the user") @RequestParam Long userId) {
        List<Loan> loans = loanService.findLoansByUserId(userId);
        return loans.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loans);
    }

    @Operation(summary = "Find all loans with user info")
    @GetMapping("/all-with-users")
    public ResponseEntity<List<LoanWithUser>> getAllLoansWithUserInfo() {
        List<LoanWithUser> result = loanService.getAllLoansWithUserInfo();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get user info for loan")
    @GetMapping("/user-by-loan")
    public ResponseEntity<User> getUserByLoanId(@RequestParam Long loanId) {
        User user = loanService.getUserByLoanId(loanId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}
