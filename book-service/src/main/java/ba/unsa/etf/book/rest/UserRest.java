package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.api.service.UserService;
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

@Tag(name = "user", description = "User API")
@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserRest {
    private UserService userService;

    @Operation(summary = "Find users")
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @Operation(summary = "Find user by ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<User> findById(
            @Parameter(required = true, description = "ID of the user", name = "id") @PathVariable Long id) {
        User user = userService.findById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create user")
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User createdUser = userService.create(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.update(user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User with ID " + id + " was deleted successfully.");
    }

    @Operation(summary = "Find all users with pagination and sorting")
    @GetMapping("/paginated")
    public ResponseEntity<Page<User>> findAllPaginated(
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
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Batch create users")
    @PostMapping("/batch")
    public ResponseEntity<List<User>> createBatch(@RequestBody List<User> users) {
        List<User> createdUsers = userService.createBatch(users);
        return ResponseEntity.ok(createdUsers);
    }

    @Operation(summary = "Find users by first name and last name")
    @GetMapping("/search")
    public ResponseEntity<List<User>> findByFirstNameAndLastName(
            @Parameter(description = "First name of the user") @RequestParam String firstName,
            @Parameter(description = "Last name of the user") @RequestParam String lastName) {
        List<User> users = userService.findByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Find user by email")
    @GetMapping("/email")
    public ResponseEntity<User> findByEmail(
            @Parameter(description = "Email of the user") @RequestParam String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }
}
