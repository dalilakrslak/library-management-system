package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.Author;
import ba.unsa.etf.book.dao.model.AuthorEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-13T10:55:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public Author entityToDto(AuthorEntity authorEntity) {
        if ( authorEntity == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;
        String biography = null;

        id = authorEntity.getId();
        firstName = authorEntity.getFirstName();
        lastName = authorEntity.getLastName();
        biography = authorEntity.getBiography();

        Author author = new Author( id, firstName, lastName, biography );

        return author;
    }

    @Override
    public AuthorEntity dtoToEntity(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorEntity authorEntity = new AuthorEntity();

        authorEntity.setId( author.getId() );
        authorEntity.setFirstName( author.getFirstName() );
        authorEntity.setLastName( author.getLastName() );
        authorEntity.setBiography( author.getBiography() );

        return authorEntity;
    }

    @Override
    public void updateEntity(Author author, AuthorEntity authorEntity) {
        if ( author == null ) {
            return;
        }

        authorEntity.setId( author.getId() );
        authorEntity.setFirstName( author.getFirstName() );
        authorEntity.setLastName( author.getLastName() );
        authorEntity.setBiography( author.getBiography() );
    }
}
