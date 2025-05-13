package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.BookVersion;
import ba.unsa.etf.transfer.dao.model.BookEntity;
import ba.unsa.etf.transfer.dao.model.BookVersionEntity;
import ba.unsa.etf.transfer.dao.model.LibraryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T21:57:20+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class BookVersionMapperImpl implements BookVersionMapper {

    @Override
    public BookVersion entityToDto(BookVersionEntity BookVersionEntity) {
        if ( BookVersionEntity == null ) {
            return null;
        }

        Long bookId = null;
        Long libraryId = null;
        String isbn = null;
        Boolean returned = null;
        Boolean reserved = null;

        bookId = bookVersionEntityBookId( BookVersionEntity );
        libraryId = bookVersionEntityLibraryId( BookVersionEntity );
        isbn = BookVersionEntity.getIsbn();
        returned = BookVersionEntity.isReturned();
        reserved = BookVersionEntity.isReserved();

        BookVersion bookVersion = new BookVersion( isbn, bookId, libraryId, returned, reserved );

        return bookVersion;
    }

    @Override
    public BookVersionEntity dtoToEntity(BookVersion bookVersion) {
        if ( bookVersion == null ) {
            return null;
        }

        BookVersionEntity bookVersionEntity = new BookVersionEntity();

        bookVersionEntity.setBook( bookVersionToBookEntity( bookVersion ) );
        bookVersionEntity.setLibrary( bookVersionToLibraryEntity( bookVersion ) );
        bookVersionEntity.setIsbn( bookVersion.getIsbn() );
        if ( bookVersion.getReturned() != null ) {
            bookVersionEntity.setReturned( bookVersion.getReturned() );
        }
        if ( bookVersion.getReserved() != null ) {
            bookVersionEntity.setReserved( bookVersion.getReserved() );
        }

        return bookVersionEntity;
    }

    @Override
    public void updateEntity(BookVersion bookVersion, BookVersionEntity bookVersionEntity) {
        if ( bookVersion == null ) {
            return;
        }

        if ( bookVersionEntity.getBook() == null ) {
            bookVersionEntity.setBook( new BookEntity() );
        }
        bookVersionToBookEntity1( bookVersion, bookVersionEntity.getBook() );
        if ( bookVersionEntity.getLibrary() == null ) {
            bookVersionEntity.setLibrary( new LibraryEntity() );
        }
        bookVersionToLibraryEntity1( bookVersion, bookVersionEntity.getLibrary() );
        bookVersionEntity.setIsbn( bookVersion.getIsbn() );
        if ( bookVersion.getReturned() != null ) {
            bookVersionEntity.setReturned( bookVersion.getReturned() );
        }
        if ( bookVersion.getReserved() != null ) {
            bookVersionEntity.setReserved( bookVersion.getReserved() );
        }
    }

    private Long bookVersionEntityBookId(BookVersionEntity bookVersionEntity) {
        if ( bookVersionEntity == null ) {
            return null;
        }
        BookEntity book = bookVersionEntity.getBook();
        if ( book == null ) {
            return null;
        }
        Long id = book.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long bookVersionEntityLibraryId(BookVersionEntity bookVersionEntity) {
        if ( bookVersionEntity == null ) {
            return null;
        }
        LibraryEntity library = bookVersionEntity.getLibrary();
        if ( library == null ) {
            return null;
        }
        Long id = library.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected BookEntity bookVersionToBookEntity(BookVersion bookVersion) {
        if ( bookVersion == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setId( bookVersion.getBookId() );

        return bookEntity;
    }

    protected LibraryEntity bookVersionToLibraryEntity(BookVersion bookVersion) {
        if ( bookVersion == null ) {
            return null;
        }

        LibraryEntity libraryEntity = new LibraryEntity();

        libraryEntity.setId( bookVersion.getLibraryId() );

        return libraryEntity;
    }

    protected void bookVersionToBookEntity1(BookVersion bookVersion, BookEntity mappingTarget) {
        if ( bookVersion == null ) {
            return;
        }

        mappingTarget.setId( bookVersion.getBookId() );
    }

    protected void bookVersionToLibraryEntity1(BookVersion bookVersion, LibraryEntity mappingTarget) {
        if ( bookVersion == null ) {
            return;
        }

        mappingTarget.setId( bookVersion.getLibraryId() );
    }
}
