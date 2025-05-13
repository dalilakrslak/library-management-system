package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.Book;
import ba.unsa.etf.transfer.dao.model.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mappings({
            @Mapping(source = "author.id", target = "authorId"),
            @Mapping(source = "genre.id", target = "genreId")
    })
    Book entityToDto(BookEntity bookEntity);

    @Mappings({
            @Mapping(source = "authorId", target = "author.id"),
            @Mapping(source = "genreId", target = "genre.id")
    })
    BookEntity dtoToEntity(Book book);

    @Mappings({
            @Mapping(source = "authorId", target = "author.id"),
            @Mapping(source = "genreId", target = "genre.id")
    })
    void updateEntity(Book book, @MappingTarget BookEntity bookEntity);
}
