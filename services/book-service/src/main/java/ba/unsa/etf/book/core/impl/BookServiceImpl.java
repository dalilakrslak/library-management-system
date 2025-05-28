package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.*;
import ba.unsa.etf.book.api.service.BookService;
import ba.unsa.etf.book.core.mapper.BookMapper;
import ba.unsa.etf.book.core.mapper.BookVersionMapper;
import ba.unsa.etf.book.core.validation.BookValidation;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.repository.BookRepository;
import ba.unsa.etf.book.dao.repository.BookVersionRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookValidation bookValidation;
    private final BookVersionRepository bookVersionRepository;
    private final BookVersionMapper bookVersionMapper;
    private final RestTemplate restTemplate;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Book findById(Long id) {
        bookValidation.exists(id);
        return bookRepository.findById(id).map(bookMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Book create(Book book) {
        BookEntity bookEntity = bookMapper.dtoToEntity(book);
        bookRepository.save(bookEntity);
        return bookMapper.entityToDto(bookEntity);
    }

    @Override
    @Transactional
    public Book update(Book book) {
        BookEntity bookEntity = bookMapper.dtoToEntity(book);
        bookRepository.save(bookEntity);
        return bookMapper.entityToDto(bookEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookValidation.exists(id);
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::entityToDto);
    }

    @Override
    public List<Book> createBatch(List<Book> books) {
        List<BookEntity> bookEntities = books.stream().map(bookMapper::dtoToEntity).collect(Collectors.toList());
        bookEntities.addAll(bookRepository.saveAll(bookEntities));
        return bookEntities.stream().map(bookMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        List<BookEntity> bookEntities = bookRepository.findBooksByAuthor(authorName);
        return bookEntities.stream().map(bookMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookVersion> getBookVersions(Long bookId) {
        List<BookVersionEntity> bookVersions = bookVersionRepository.findByBookId(bookId);

        return bookVersions.stream().map(bookVersionMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookAvailability> getBookAvailability(Long bookId) {
        List<BookVersionEntity> bookVersions = bookVersionRepository.findByBookId(bookId);
        Map<Long, BookAvailability> availabilityMap = new HashMap<>();

        String url = "http://library-service/library";
        Library[] librariesArray = restTemplate.getForObject(url, Library[].class);
        Map<Long, Library> libraryMap = Arrays.stream(librariesArray)
                .collect(Collectors.toMap(Library::getId, Function.identity()));

        List<String> bookVersionIsbns = bookVersions.stream()
                .map(BookVersionEntity::getIsbn)
                .filter(Objects::nonNull)
                .toList();

        String transferUrl = "http://transfer-service/transfer/by-book-version?bookVersions=" +
                String.join(",", bookVersionIsbns);
        Transfer[] transferArray = restTemplate.getForObject(transferUrl, Transfer[].class);
        List<Transfer> transfers = Arrays.asList(transferArray);

        Set<String> transferIsbns = transfers.stream()
                .map(Transfer::getBookVersion)
                .collect(Collectors.toSet());

        for (BookVersionEntity bookVersion : bookVersions) {
            Long libraryId = bookVersion.getLibraryId();
            BookAvailability availability = availabilityMap.getOrDefault(libraryId, new BookAvailability());

            Library library = libraryMap.get(libraryId);
            if (library == null) continue;

            availability.setLibraryId(libraryId);
            availability.setLibraryName(library.getName());
            availability.setTotalCount(availability.getTotalCount() + 1);

            if (transferIsbns.contains(bookVersion.getIsbn())) {
                availability.setTransferCount(availability.getTransferCount() + 1);
                availabilityMap.put(libraryId, availability);
                continue;
            }

            if (Boolean.TRUE.equals(bookVersion.getIsCheckedOut())) {
                availability.setCheckedOutCount(availability.getCheckedOutCount() + 1);
            }
            else if (Boolean.TRUE.equals(bookVersion.getIsReserved())) {
                availability.setReservedCount(availability.getReservedCount() + 1);
            }
            else {
                availability.setAvailableCount(availability.getAvailableCount() + 1);
            }

            availabilityMap.put(libraryId, availability);
        }

        return new ArrayList<>(availabilityMap.values());
    }
}
