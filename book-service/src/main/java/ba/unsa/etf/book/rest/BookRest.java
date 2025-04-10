package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.service.BookService;
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

@Tag(name = "book", description = "Book API")
@RestController
@RequestMapping(value = "/book")
@AllArgsConstructor
public class BookRest {
    private BookService bookService;

    @Operation(summary = "Find books")
    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @Operation(summary = "Find book by ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Book> findById(
            @Parameter(required = true, description = "ID of the book", name = "id") @PathVariable Long id) {
        Book book = bookService.findById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create book")
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book createdBook = bookService.create(book);
        return ResponseEntity.ok(createdBook);
    }

    @Operation(summary = "Update book")
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        Book updatedBook = bookService.update(book);
        return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete book")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok("Book with ID " + id + " was deleted successfully.");
    }

    @Operation(summary = "Find all books with pagination and sorting")
    @GetMapping("/paginated")
    public ResponseEntity<Page<Book>> findAllPaginated(
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
        Page<Book> books = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Batch create books")
    @PostMapping("/batch")
    public ResponseEntity<List<Book>> createBatch(@RequestBody List<Book> books) {
        List<Book> createdBooks = bookService.createBatch(books);
        return ResponseEntity.ok(createdBooks);
    }

    @Operation(summary = "Find books by author name")
    @GetMapping("/search")
    public ResponseEntity<List<Book>> findBooksByAuthor(
            @Parameter(description = "Name of the author") @RequestParam String authorName) {

        List<Book> books = bookService.findBooksByAuthor(authorName);

        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }
}
