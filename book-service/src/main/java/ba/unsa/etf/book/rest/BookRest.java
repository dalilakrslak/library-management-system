package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
}
