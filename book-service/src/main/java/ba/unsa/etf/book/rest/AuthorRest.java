package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Author;
import ba.unsa.etf.book.api.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "author", description = "Author API")
@RestController
@RequestMapping(value = "/author")
@AllArgsConstructor
public class AuthorRest {
    private AuthorService authorService;

    @Operation(summary = "Find authors")
    @GetMapping
    public List<Author> findAll() {
        return authorService.findAll();
    }

    @Operation(summary = "Find author by ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Author> findById(
            @Parameter(required = true, description = "ID of the author", name = "id") @PathVariable Long id) {
        Author author = authorService.findById(id);
        return ResponseEntity.ok(author);
    }

    @Operation(summary = "Create author")
    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        Author createdAuthor = authorService.create(author);
        return ResponseEntity.ok(createdAuthor);
    }

    @Operation(summary = "Update author")
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author author) {
        author.setId(id);
        Author updatedAuthor = authorService.update(author);
        return ResponseEntity.ok(updatedAuthor);
    }

    @Operation(summary = "Delete author")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.ok("Author with ID " + id + " was deleted successfully.");
    }

    @Operation(summary = "Find all authors with pagination and sorting")
    @GetMapping("/paginated")
    public ResponseEntity<Page<Author>> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName,asc") String[] sort
    ) {
        Sort sortOrder = Sort.by(
                sort[1].equalsIgnoreCase("desc") ?
                        Sort.Order.desc(sort[0]) :
                        Sort.Order.asc(sort[0])
        );
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Author> authors = authorService.getAllAuthors(pageable);
        return ResponseEntity.ok(authors);
    }
}