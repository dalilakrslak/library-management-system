package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.Library;
import ba.unsa.etf.transfer.dao.model.LibraryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T21:57:20+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class LibraryMapperImpl implements LibraryMapper {

    @Override
    public Library entityToDto(LibraryEntity libraryEntity) {
        if ( libraryEntity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String address = null;
        String contact = null;

        id = libraryEntity.getId();
        name = libraryEntity.getName();
        address = libraryEntity.getAddress();
        contact = libraryEntity.getContact();

        Library library = new Library( id, name, address, contact );

        return library;
    }

    @Override
    public LibraryEntity dtoToEntity(Library library) {
        if ( library == null ) {
            return null;
        }

        LibraryEntity libraryEntity = new LibraryEntity();

        libraryEntity.setId( library.getId() );
        libraryEntity.setName( library.getName() );
        libraryEntity.setAddress( library.getAddress() );
        libraryEntity.setContact( library.getContact() );

        return libraryEntity;
    }

    @Override
    public void updateEntity(Library library, LibraryEntity libraryEntity) {
        if ( library == null ) {
            return;
        }

        libraryEntity.setId( library.getId() );
        libraryEntity.setName( library.getName() );
        libraryEntity.setAddress( library.getAddress() );
        libraryEntity.setContact( library.getContact() );
    }
}
