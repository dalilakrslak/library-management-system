package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.Transfer;
import ba.unsa.etf.transfer.dao.model.BookVersionEntity;
import ba.unsa.etf.transfer.dao.model.LibraryEntity;
import ba.unsa.etf.transfer.dao.model.TransferEntity;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T21:57:21+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class TransferMapperImpl implements TransferMapper {

    @Override
    public Transfer entityToDto(TransferEntity transferEntity) {
        if ( transferEntity == null ) {
            return null;
        }

        Long bookVersionISBN = null;
        Long libraryFrom = null;
        Long libraryTo = null;
        Long id = null;
        LocalDate transferDate = null;

        String isbn = transferEntityBookVersionIsbn( transferEntity );
        if ( isbn != null ) {
            bookVersionISBN = Long.parseLong( isbn );
        }
        libraryFrom = transferEntityLibraryFromId( transferEntity );
        libraryTo = transferEntityLibraryToId( transferEntity );
        id = transferEntity.getId();
        transferDate = transferEntity.getTransferDate();

        Transfer transfer = new Transfer( id, bookVersionISBN, libraryFrom, libraryTo, transferDate );

        return transfer;
    }

    @Override
    public TransferEntity dtoToEntity(Transfer transfer) {
        if ( transfer == null ) {
            return null;
        }

        TransferEntity transferEntity = new TransferEntity();

        transferEntity.setBookVersion( transferToBookVersionEntity( transfer ) );
        transferEntity.setLibraryFrom( transferToLibraryEntity( transfer ) );
        transferEntity.setLibraryTo( transferToLibraryEntity1( transfer ) );
        transferEntity.setId( transfer.getId() );
        transferEntity.setTransferDate( transfer.getTransferDate() );

        return transferEntity;
    }

    @Override
    public void updateEntity(Transfer transfer, TransferEntity transferEntity) {
        if ( transfer == null ) {
            return;
        }

        if ( transferEntity.getBookVersion() == null ) {
            transferEntity.setBookVersion( new BookVersionEntity() );
        }
        transferToBookVersionEntity1( transfer, transferEntity.getBookVersion() );
        if ( transferEntity.getLibraryFrom() == null ) {
            transferEntity.setLibraryFrom( new LibraryEntity() );
        }
        transferToLibraryEntity2( transfer, transferEntity.getLibraryFrom() );
        if ( transferEntity.getLibraryTo() == null ) {
            transferEntity.setLibraryTo( new LibraryEntity() );
        }
        transferToLibraryEntity3( transfer, transferEntity.getLibraryTo() );
        transferEntity.setId( transfer.getId() );
        transferEntity.setTransferDate( transfer.getTransferDate() );
    }

    private String transferEntityBookVersionIsbn(TransferEntity transferEntity) {
        if ( transferEntity == null ) {
            return null;
        }
        BookVersionEntity bookVersion = transferEntity.getBookVersion();
        if ( bookVersion == null ) {
            return null;
        }
        String isbn = bookVersion.getIsbn();
        if ( isbn == null ) {
            return null;
        }
        return isbn;
    }

    private Long transferEntityLibraryFromId(TransferEntity transferEntity) {
        if ( transferEntity == null ) {
            return null;
        }
        LibraryEntity libraryFrom = transferEntity.getLibraryFrom();
        if ( libraryFrom == null ) {
            return null;
        }
        Long id = libraryFrom.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long transferEntityLibraryToId(TransferEntity transferEntity) {
        if ( transferEntity == null ) {
            return null;
        }
        LibraryEntity libraryTo = transferEntity.getLibraryTo();
        if ( libraryTo == null ) {
            return null;
        }
        Long id = libraryTo.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected BookVersionEntity transferToBookVersionEntity(Transfer transfer) {
        if ( transfer == null ) {
            return null;
        }

        BookVersionEntity bookVersionEntity = new BookVersionEntity();

        if ( transfer.getBookVersionISBN() != null ) {
            bookVersionEntity.setIsbn( String.valueOf( transfer.getBookVersionISBN() ) );
        }

        return bookVersionEntity;
    }

    protected LibraryEntity transferToLibraryEntity(Transfer transfer) {
        if ( transfer == null ) {
            return null;
        }

        LibraryEntity libraryEntity = new LibraryEntity();

        libraryEntity.setId( transfer.getLibraryFrom() );

        return libraryEntity;
    }

    protected LibraryEntity transferToLibraryEntity1(Transfer transfer) {
        if ( transfer == null ) {
            return null;
        }

        LibraryEntity libraryEntity = new LibraryEntity();

        libraryEntity.setId( transfer.getLibraryTo() );

        return libraryEntity;
    }

    protected void transferToBookVersionEntity1(Transfer transfer, BookVersionEntity mappingTarget) {
        if ( transfer == null ) {
            return;
        }

        if ( transfer.getBookVersionISBN() != null ) {
            mappingTarget.setIsbn( String.valueOf( transfer.getBookVersionISBN() ) );
        }
        else {
            mappingTarget.setIsbn( null );
        }
    }

    protected void transferToLibraryEntity2(Transfer transfer, LibraryEntity mappingTarget) {
        if ( transfer == null ) {
            return;
        }

        mappingTarget.setId( transfer.getLibraryFrom() );
    }

    protected void transferToLibraryEntity3(Transfer transfer, LibraryEntity mappingTarget) {
        if ( transfer == null ) {
            return;
        }

        mappingTarget.setId( transfer.getLibraryTo() );
    }
}
