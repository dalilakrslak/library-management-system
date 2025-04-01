package ba.unsa.etf.transfer.rest;

import ba.unsa.etf.transfer.api.model.Transfer;
import ba.unsa.etf.transfer.api.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "transfer", description = "Transfer API")
@RestController
@RequestMapping(value = "/transfer")
@AllArgsConstructor
public class TransferRest {
    private TransferService transferService;

    @Operation(summary = "Find transfers")
    @GetMapping
    public List<Transfer> findAll() {
        return transferService.findAll();
    }

    @Operation(summary = "Find transfer by ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Transfer> findById(
            @Parameter(required = true, description = "ID of the transfer", name = "id") @PathVariable Long id) {
        Transfer transfer = transferService.findById(id);
        return transfer != null ? ResponseEntity.ok(transfer) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create transfer")
    @PostMapping
    public ResponseEntity<Transfer> create(@RequestBody Transfer transfer) {
        Transfer createdTransfer = transferService.create(transfer);
        return ResponseEntity.ok(createdTransfer);
    }

    @Operation(summary = "Update transfer")
    @PutMapping("/{id}")
    public ResponseEntity<Transfer> update(@PathVariable Long id, @RequestBody Transfer transfer) {
        transfer.setId(id);
        Transfer upddatedTransfer = transferService.update(transfer);
        return upddatedTransfer != null ? ResponseEntity.ok(upddatedTransfer) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete transfer")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        transferService.delete(id);
        return ResponseEntity.ok("Transfer with ID " + id + " was deleted successfully.");
    }
}
