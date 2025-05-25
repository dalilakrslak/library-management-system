package ba.unsa.etf.transfer.rest;

import ba.unsa.etf.transfer.api.model.Genre;
import ba.unsa.etf.transfer.api.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "genre", description = "Genre API")
@RestController
@RequestMapping(value = "/genre")
@AllArgsConstructor
public class GenreRest {
    private GenreService genreService;

    @Operation(summary = "Find genres")
    @GetMapping
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @Operation(summary = "Find genre by ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Genre> findById(
            @Parameter(required = true, description = "ID of the genre", name = "id") @PathVariable Long id) {
        Genre genre = genreService.findById(id);
        return genre != null ? ResponseEntity.ok(genre) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create genre")
    @PostMapping
    public ResponseEntity<Genre> create(@RequestBody Genre genre) {
        Genre createdGenre = genreService.create(genre);
        return ResponseEntity.ok(createdGenre);
    }

    @Operation(summary = "Update genre")
    @PutMapping("/{id}")
    public ResponseEntity<Genre> update(@PathVariable Long id, @RequestBody Genre genre) {
        genre.setId(id);
        Genre updatedGenre = genreService.update(genre);
        return updatedGenre != null ? ResponseEntity.ok(updatedGenre) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete genre")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.ok("Genre with ID " + id + " was deleted successfully.");
    }
}
