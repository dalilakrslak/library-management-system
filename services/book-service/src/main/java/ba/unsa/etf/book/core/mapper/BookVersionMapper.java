package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.BookVersion;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookVersionMapper {
    @Mapping(source = "book.id", target = "bookId")
    BookVersion entityToDto(BookVersionEntity bookVersionEntity);

    @Mapping(source = "bookId", target = "book.id")
    BookVersionEntity dtoToEntity(BookVersion bookVersion);

    @Mapping(source = "bookId", target = "book.id")
    void updateEntity(BookVersion bookVersion, @MappingTarget BookVersionEntity bookVersionEntity);
}
