package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.dao.model.AuthorEntity;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.model.GenreEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-13T10:55:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public Book entityToDto(BookEntity bookEntity) {
        if ( bookEntity == null ) {
            return null;
        }

        Long authorId = null;
        Long genreId = null;
        Long id = null;
        String title = null;
        String description = null;
        int pageCount = 0;
        int publicationYear = 0;
        String language = null;

        authorId = bookEntityAuthorId( bookEntity );
        genreId = bookEntityGenreId( bookEntity );
        id = bookEntity.getId();
        title = bookEntity.getTitle();
        description = bookEntity.getDescription();
        pageCount = bookEntity.getPageCount();
        publicationYear = bookEntity.getPublicationYear();
        language = bookEntity.getLanguage();

        Book book = new Book( id, title, description, pageCount, publicationYear, language, authorId, genreId );

        return book;
    }

    @Override
    public BookEntity dtoToEntity(Book book) {
        if ( book == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setAuthor( bookToAuthorEntity( book ) );
        bookEntity.setGenre( bookToGenreEntity( book ) );
        bookEntity.setId( book.getId() );
        bookEntity.setTitle( book.getTitle() );
        bookEntity.setDescription( book.getDescription() );
        bookEntity.setPageCount( book.getPageCount() );
        bookEntity.setPublicationYear( book.getPublicationYear() );
        bookEntity.setLanguage( book.getLanguage() );

        return bookEntity;
    }

    @Override
    public void updateEntity(Book book, BookEntity bookEntity) {
        if ( book == null ) {
            return;
        }

        if ( bookEntity.getAuthor() == null ) {
            bookEntity.setAuthor( new AuthorEntity() );
        }
        bookToAuthorEntity1( book, bookEntity.getAuthor() );
        if ( bookEntity.getGenre() == null ) {
            bookEntity.setGenre( new GenreEntity() );
        }
        bookToGenreEntity1( book, bookEntity.getGenre() );
        bookEntity.setId( book.getId() );
        bookEntity.setTitle( book.getTitle() );
        bookEntity.setDescription( book.getDescription() );
        bookEntity.setPageCount( book.getPageCount() );
        bookEntity.setPublicationYear( book.getPublicationYear() );
        bookEntity.setLanguage( book.getLanguage() );
    }

    private Long bookEntityAuthorId(BookEntity bookEntity) {
        if ( bookEntity == null ) {
            return null;
        }
        AuthorEntity author = bookEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long bookEntityGenreId(BookEntity bookEntity) {
        if ( bookEntity == null ) {
            return null;
        }
        GenreEntity genre = bookEntity.getGenre();
        if ( genre == null ) {
            return null;
        }
        Long id = genre.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected AuthorEntity bookToAuthorEntity(Book book) {
        if ( book == null ) {
            return null;
        }

        AuthorEntity authorEntity = new AuthorEntity();

        authorEntity.setId( book.getAuthorId() );

        return authorEntity;
    }

    protected GenreEntity bookToGenreEntity(Book book) {
        if ( book == null ) {
            return null;
        }

        GenreEntity genreEntity = new GenreEntity();

        genreEntity.setId( book.getGenreId() );

        return genreEntity;
    }

    protected void bookToAuthorEntity1(Book book, AuthorEntity mappingTarget) {
        if ( book == null ) {
            return;
        }

        mappingTarget.setId( book.getAuthorId() );
    }

    protected void bookToGenreEntity1(Book book, GenreEntity mappingTarget) {
        if ( book == null ) {
            return;
        }

        mappingTarget.setId( book.getGenreId() );
    }
}
