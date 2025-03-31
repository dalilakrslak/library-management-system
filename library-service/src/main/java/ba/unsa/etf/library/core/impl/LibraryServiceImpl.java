package ba.unsa.etf.library.core.impl;

import ba.unsa.etf.library.api.model.Library;
import ba.unsa.etf.library.api.service.LibraryService;
import ba.unsa.etf.library.core.mapper.LibraryMapper;
import ba.unsa.etf.library.core.validation.LibraryValidation;
import ba.unsa.etf.library.dao.model.LibraryEntity;
import ba.unsa.etf.library.dao.repository.LibraryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;
    private final LibraryValidation libraryValidation;

    @Override
    public List<Library> findAll() {
        return libraryRepository.findAll()
                .stream()
                .map(libraryMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Library findById(Long id) {
        libraryValidation.exists(id);
        return libraryRepository.findById(id).map(libraryMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Library create(Library library) {
        LibraryEntity libraryEntity = libraryMapper.dtoToEntity(library);
        libraryRepository.save(libraryEntity);
        return libraryMapper.entityToDto(libraryEntity);
    }

    @Override
    @Transactional
    public Library update(Library library) {
        LibraryEntity libraryEntity = libraryMapper.dtoToEntity(library);
        libraryRepository.save(libraryEntity);
        return libraryMapper.entityToDto(libraryEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        libraryValidation.exists(id);
        libraryRepository.deleteById(id);
    }
}
