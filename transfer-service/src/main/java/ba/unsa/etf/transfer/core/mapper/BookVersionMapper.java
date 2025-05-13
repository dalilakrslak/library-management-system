package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.Book;
import ba.unsa.etf.transfer.api.model.BookVersion;
import ba.unsa.etf.transfer.dao.model.BookEntity;
import ba.unsa.etf.transfer.dao.model.BookVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookVersionMapper {
    @Mappings({
            @Mapping(source = "book.id", target = "bookId"),
            @Mapping(source = "library.id", target = "libraryId")
    })
    BookVersion entityToDto(BookVersionEntity BookVersionEntity);

    @Mappings({
            @Mapping(source = "bookId", target = "book.id"),
            @Mapping(source = "libraryId", target = "library.id")
    })
    BookVersionEntity dtoToEntity(BookVersion bookVersion);

    @Mappings({
            @Mapping(source = "bookId", target = "book.id"),
            @Mapping(source = "libraryId", target = "library.id")
    })
    void updateEntity(BookVersion bookVersion, @MappingTarget BookVersionEntity bookVersionEntity);
}
