package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Loan;
import ba.unsa.etf.book.api.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
}
