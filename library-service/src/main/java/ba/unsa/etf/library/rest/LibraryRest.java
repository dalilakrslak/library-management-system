package ba.unsa.etf.library.rest;

import ba.unsa.etf.library.api.model.Library;
import ba.unsa.etf.library.api.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "library", description = "Library API")
@RestController
@RequestMapping(value = "/library")
@AllArgsConstructor
public class LibraryRest {
    private final LibraryService libraryService;

    @Operation(summary = "Find all libraries")
    @GetMapping
    public List<Library> findAll() {
        return libraryService.findAll();
    }

    @Operation(summary = "Find library by ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Library> findById(
            @Parameter(required = true, description = "ID of the library", name = "id") @PathVariable Long id) {
        Library library = libraryService.findById(id);
        return ResponseEntity.ok(library);
    }

    @Operation(summary = "Create a new library")
    @PostMapping
    public ResponseEntity<Library> create(@RequestBody Library library) {
        Library createdLibrary = libraryService.create(library);
        return ResponseEntity.ok(createdLibrary);
    }

    @Operation(summary = "Update library")
    @PutMapping("/{id}")
    public ResponseEntity<Library> update(@PathVariable Long id, @RequestBody Library library) {
        library.setId(id);
        Library updatedLibrary = libraryService.update(library);
        return updatedLibrary != null ? ResponseEntity.ok(updatedLibrary) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete library")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        libraryService.delete(id);
        return ResponseEntity.ok("Library with ID " + id + " was deleted successfully.");
    }
}