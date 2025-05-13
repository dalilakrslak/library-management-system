package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.Genre;
import ba.unsa.etf.transfer.dao.model.GenreEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T21:57:21+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class GenreMapperImpl implements GenreMapper {

    @Override
    public Genre entityToDto(GenreEntity genreEntity) {
        if ( genreEntity == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = genreEntity.getId();
        name = genreEntity.getName();

        Genre genre = new Genre( id, name );

        return genre;
    }

    @Override
    public GenreEntity dtoToEntity(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        GenreEntity genreEntity = new GenreEntity();

        genreEntity.setId( genre.getId() );
        genreEntity.setName( genre.getName() );

        return genreEntity;
    }

    @Override
    public void updateEntity(Genre genre, GenreEntity genreEntity) {
        if ( genre == null ) {
            return;
        }

        genreEntity.setId( genre.getId() );
        genreEntity.setName( genre.getName() );
    }
}
