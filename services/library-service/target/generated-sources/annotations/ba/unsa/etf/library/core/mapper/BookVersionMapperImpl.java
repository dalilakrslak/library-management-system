package ba.unsa.etf.library.core.mapper;

import ba.unsa.etf.library.api.model.BookVersion;
import ba.unsa.etf.library.dao.model.BookVersionEntity;
import ba.unsa.etf.library.dao.model.LibraryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T21:48:11+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class BookVersionMapperImpl implements BookVersionMapper {

    @Override
    public BookVersion entityToDto(BookVersionEntity bookVersionEntity) {
        if ( bookVersionEntity == null ) {
            return null;
        }

        Long libraryId = null;
        String isbn = null;
        Boolean returned = null;
        Boolean reserved = null;

        libraryId = bookVersionEntityLibraryId( bookVersionEntity );
        isbn = bookVersionEntity.getIsbn();
        returned = bookVersionEntity.getReturned();
        reserved = bookVersionEntity.getReserved();

        BookVersion bookVersion = new BookVersion( isbn, libraryId, returned, reserved );

        return bookVersion;
    }

    @Override
    public BookVersionEntity dtoToEntity(BookVersion bookVersion) {
        if ( bookVersion == null ) {
            return null;
        }

        BookVersionEntity bookVersionEntity = new BookVersionEntity();

        bookVersionEntity.setLibrary( bookVersionToLibraryEntity( bookVersion ) );
        bookVersionEntity.setIsbn( bookVersion.getIsbn() );
        bookVersionEntity.setReturned( bookVersion.getReturned() );
        bookVersionEntity.setReserved( bookVersion.getReserved() );

        return bookVersionEntity;
    }

    @Override
    public void updateEntity(BookVersion bookVersion, BookVersionEntity bookVersionEntity) {
        if ( bookVersion == null ) {
            return;
        }

        if ( bookVersionEntity.getLibrary() == null ) {
            bookVersionEntity.setLibrary( new LibraryEntity() );
        }
        bookVersionToLibraryEntity1( bookVersion, bookVersionEntity.getLibrary() );
        bookVersionEntity.setIsbn( bookVersion.getIsbn() );
        bookVersionEntity.setReturned( bookVersion.getReturned() );
        bookVersionEntity.setReserved( bookVersion.getReserved() );
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

    protected LibraryEntity bookVersionToLibraryEntity(BookVersion bookVersion) {
        if ( bookVersion == null ) {
            return null;
        }

        LibraryEntity libraryEntity = new LibraryEntity();

        libraryEntity.setId( bookVersion.getLibraryId() );

        return libraryEntity;
    }

    protected void bookVersionToLibraryEntity1(BookVersion bookVersion, LibraryEntity mappingTarget) {
        if ( bookVersion == null ) {
            return;
        }

        mappingTarget.setId( bookVersion.getLibraryId() );
    }
}
