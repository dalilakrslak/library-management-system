package ba.unsa.etf.library.rest;

import ba.unsa.etf.library.api.model.Role;
import ba.unsa.etf.library.api.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "role", description = "Role API")
@RestController
@RequestMapping(value = "/role")
@AllArgsConstructor
public class RoleRest {
    private final RoleService roleService;

    @Operation(summary = "Find all roles")
    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @Operation(summary = "Find role by ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Role> findById(
            @Parameter(required = true, description = "ID of the role", name = "id") @PathVariable Long id) {
        Role role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }

    @Operation(summary = "Create a new role")
    @PostMapping
    public ResponseEntity<Role> create(@RequestBody Role role) {
        Role createdRole = roleService.create(role);
        return ResponseEntity.ok(createdRole);
    }

    @Operation(summary = "Update role")
    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        Role updatedRole = roleService.update(role);
        return ResponseEntity.ok(updatedRole);
    }

    @Operation(summary = "Delete role")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok("Role with ID " + id + " was deleted successfully.");
    }
}