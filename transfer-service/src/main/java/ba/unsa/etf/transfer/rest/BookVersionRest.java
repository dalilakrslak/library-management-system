package ba.unsa.etf.transfer.rest;

import ba.unsa.etf.transfer.api.model.BookVersion;
import ba.unsa.etf.transfer.api.service.BookVersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "book-version", description = "Book version API")
@RestController
@RequestMapping(value = "/book-version")
@AllArgsConstructor
public class BookVersionRest {
    private BookVersionService bookVersionService;

    @Operation(summary = "Find book versions")
    @GetMapping
    public List<BookVersion> findAll() {
        return bookVersionService.findAll();
    }

    @Operation(summary = "Find book version by ID")
    @GetMapping(value = "{isbn}")
    public ResponseEntity<BookVersion> findById(
            @Parameter(required = true, description = "ISBN of the book version", name = "isbn") @PathVariable String isbn) {
        BookVersion bookVersion = bookVersionService.findById(isbn);
        return bookVersion != null ? ResponseEntity.ok(bookVersion) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create book version")
    @PostMapping
    public ResponseEntity<BookVersion> create(@RequestBody BookVersion bookVersion) {
        BookVersion createdBookVersion = bookVersionService.create(bookVersion);
        return ResponseEntity.ok(createdBookVersion);
    }

    @Operation(summary = "Update book version")
    @PutMapping("/{isbn}")
    public ResponseEntity<BookVersion> update(@PathVariable String isbn, @RequestBody BookVersion bookVersion) {
        bookVersion.setIsbn(isbn);
        BookVersion updatedBookVersion = bookVersionService.update(bookVersion);
        return updatedBookVersion != null ? ResponseEntity.ok(updatedBookVersion) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete book version")
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> delete(@PathVariable String isbn) {
        bookVersionService.delete(isbn);
        return ResponseEntity.ok("Book version with ISBN " + isbn + " was deleted successfully.");
    }
}
