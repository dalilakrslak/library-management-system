package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.model.BookVersion;
import ba.unsa.etf.book.api.service.BookVersionService;
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

    @Operation(summary = "Find all books with pagination and sorting")
    @GetMapping("/paginated")
    public ResponseEntity<Page<BookVersion>> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title,asc") String[] sort
    ) {
        Sort sortOrder = Sort.by(
                sort[1].equalsIgnoreCase("desc") ?
                        Sort.Order.desc(sort[0]) :
                        Sort.Order.asc(sort[0])
        );
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<BookVersion> books = bookVersionService.getAllBooks(pageable);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Batch create books")
    @PostMapping("/batch")
    public ResponseEntity<List<BookVersion>> createBatch(@RequestBody List<BookVersion> books) {
        List<BookVersion> createdBooks = bookVersionService.createBatch(books);
        return ResponseEntity.ok(createdBooks);
    }

    @Operation(summary = "Find books by title")
    @GetMapping("/search")
    public ResponseEntity<List<BookVersion>> findBooksByTitle(
            @Parameter(description = "Title of the book") @RequestParam String title) {

        List<BookVersion> books = bookVersionService.findBooksByTitle(title);

        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }
}
