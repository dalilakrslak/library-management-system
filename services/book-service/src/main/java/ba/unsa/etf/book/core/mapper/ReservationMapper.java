package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.dao.model.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(source = "bookVersion.isbn", target = "bookVersion")
    Reservation entityToDto(ReservationEntity reservationEntity);

    @Mapping(source = "bookVersion", target = "bookVersion.isbn")
    ReservationEntity dtoToEntity(Reservation reservation);

    @Mapping(source = "bookVersion", target = "bookVersion.isbn")
    void updateEntity(Reservation reservation, @MappingTarget ReservationEntity reservationEntity);
}
