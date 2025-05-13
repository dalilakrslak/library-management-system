package ba.unsa.etf.transfer.api.service;

import ba.unsa.etf.transfer.api.model.Author;
import ba.unsa.etf.transfer.api.model.Transfer;

import java.util.List;

public interface TransferService {
    List<Transfer> findAll();

    Transfer findById(Long id);

    Transfer create(Transfer transfer);

    Transfer update(Transfer transfer);

    void delete(Long id);
}
