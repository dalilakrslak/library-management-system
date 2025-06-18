package ba.unsa.etf.transfer.dao.repository;

import ba.unsa.etf.transfer.dao.model.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {
    List<TransferEntity> findByBookVersionInAndTransferDateIsNull(List<String> bookVersions);

    @Modifying
    @Query("DELETE FROM TransferEntity t WHERE t.id = (SELECT MAX(t2.id) FROM TransferEntity t2 WHERE t2.bookVersion = :bookIsbn AND t2.libraryFrom = :libraryFrom AND t2.libraryTo = :libraryTo)")
    void deleteLastTransferForBook(@Param("bookIsbn") String bookIsbn,
                                   @Param("libraryFrom") Long libraryFrom,
                                   @Param("libraryTo") Long libraryTo);

}
