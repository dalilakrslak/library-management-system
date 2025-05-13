package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.BookVersion;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-13T10:55:28+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class BookVersionMapperImpl implements BookVersionMapper {

    @Override
    public BookVersion entityToDto(BookVersionEntity bookVersionEntity) {
        if ( bookVersionEntity == null ) {
            return null;
        }

        BookVersion bookVersion = new BookVersion();

        bookVersion.setBookId( bookVersionEntityBookId( bookVersionEntity ) );
        bookVersion.setIsbn( bookVersionEntity.getIsbn() );
        bookVersion.setIsCheckedOut( bookVersionEntity.getIsCheckedOut() );
        bookVersion.setIsReserved( bookVersionEntity.getIsReserved() );

        return bookVersion;
    }

    @Override
    public BookVersionEntity dtoToEntity(BookVersion bookVersion) {
        if ( bookVersion == null ) {
            return null;
        }

        BookVersionEntity bookVersionEntity = new BookVersionEntity();

        bookVersionEntity.setBook( bookVersionToBookEntity( bookVersion ) );
        bookVersionEntity.setIsbn( bookVersion.getIsbn() );
        bookVersionEntity.setIsCheckedOut( bookVersion.getIsCheckedOut() );
        bookVersionEntity.setIsReserved( bookVersion.getIsReserved() );

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
        bookVersionEntity.setIsbn( bookVersion.getIsbn() );
        bookVersionEntity.setIsCheckedOut( bookVersion.getIsCheckedOut() );
        bookVersionEntity.setIsReserved( bookVersion.getIsReserved() );
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

    protected BookEntity bookVersionToBookEntity(BookVersion bookVersion) {
        if ( bookVersion == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setId( bookVersion.getBookId() );

        return bookEntity;
    }

    protected void bookVersionToBookEntity1(BookVersion bookVersion, BookEntity mappingTarget) {
        if ( bookVersion == null ) {
            return;
        }

        mappingTarget.setId( bookVersion.getBookId() );
    }
}
