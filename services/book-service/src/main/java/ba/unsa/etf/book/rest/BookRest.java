package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.model.BookAvailability;
import ba.unsa.etf.book.api.model.BookVersion;
import ba.unsa.etf.book.api.service.BookService;
import com.netflix.appinfo.EurekaInstanceConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Tag(name = "book", description = "Book API")
@RestController
@RequestMapping(value = "/book")
@AllArgsConstructor
public class BookRest {
    private BookService bookService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaInstanceConfig eurekaInstanceConfig;

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

    @GetMapping("/whoami")
    public String whoami() {
        return "Book-service response from: " + eurekaInstanceConfig.getInstanceId();
    }

    @GetMapping("/self-test")
    public String selfLoadBalancedCall() {
        String response = restTemplate.getForObject("http://book-service/book/whoami", String.class);
        return "Self-called: " + response;
    }

    @Operation(summary = "Get book availability in all libraries")
    @GetMapping("/availability/{bookId}")
    public ResponseEntity<List<BookAvailability>> getBookAvailability(@Parameter(description = "ID of the book") @PathVariable Long bookId) {
        List<BookAvailability> availability = bookService.getBookAvailability(bookId);
        return availability.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(availability);
    }

    @Operation(summary = "Get all versions of a book")
    @GetMapping("/version/{bookId}")
    public ResponseEntity<List<BookVersion>> getBookVersions(@Parameter(description = "ID of the book") @PathVariable Long bookId) {
        List<BookVersion> bookVersions = bookService.getBookVersions(bookId);
        return bookVersions.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookVersions);
    }

}
