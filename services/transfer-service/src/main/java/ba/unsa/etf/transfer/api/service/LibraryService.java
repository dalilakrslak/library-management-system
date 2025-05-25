package ba.unsa.etf.transfer.api.service;

import ba.unsa.etf.transfer.api.model.Library;

import java.util.List;

public interface LibraryService {
    List<Library> findAll();

    Library findById(Long id);

    Library create(Library library);

    Library update(Library library);

    void delete(Long id);
}
