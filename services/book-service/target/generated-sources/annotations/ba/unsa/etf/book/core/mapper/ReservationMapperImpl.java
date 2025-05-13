package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.model.ReservationEntity;
import ba.unsa.etf.book.dao.model.UserEntity;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-13T10:55:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    private final DatatypeFactory datatypeFactory;

    public ReservationMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public Reservation entityToDto(ReservationEntity reservationEntity) {
        if ( reservationEntity == null ) {
            return null;
        }

        Long userId = null;
        String bookVersion = null;
        Long id = null;
        LocalDateTime reservationDate = null;

        userId = reservationEntityUserId( reservationEntity );
        bookVersion = reservationEntityBookVersionIsbn( reservationEntity );
        id = reservationEntity.getId();
        reservationDate = xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( reservationEntity.getReservationDate() ) );

        Reservation reservation = new Reservation( id, userId, bookVersion, reservationDate );

        return reservation;
    }

    @Override
    public ReservationEntity dtoToEntity(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationEntity reservationEntity = new ReservationEntity();

        reservationEntity.setUser( reservationToUserEntity( reservation ) );
        reservationEntity.setBookVersion( reservationToBookVersionEntity( reservation ) );
        reservationEntity.setId( reservation.getId() );
        reservationEntity.setReservationDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( reservation.getReservationDate() ) ) );

        return reservationEntity;
    }

    @Override
    public void updateEntity(Reservation reservation, ReservationEntity reservationEntity) {
        if ( reservation == null ) {
            return;
        }

        if ( reservationEntity.getUser() == null ) {
            reservationEntity.setUser( new UserEntity() );
        }
        reservationToUserEntity1( reservation, reservationEntity.getUser() );
        if ( reservationEntity.getBookVersion() == null ) {
            reservationEntity.setBookVersion( new BookVersionEntity() );
        }
        reservationToBookVersionEntity1( reservation, reservationEntity.getBookVersion() );
        reservationEntity.setId( reservation.getId() );
        reservationEntity.setReservationDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( reservation.getReservationDate() ) ) );
    }

    private XMLGregorianCalendar localDateToXmlGregorianCalendar( LocalDate localDate ) {
        if ( localDate == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendarDate(
            localDate.getYear(),
            localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar( LocalDateTime localDateTime ) {
        if ( localDateTime == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendar(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.get( ChronoField.MILLI_OF_SECOND ),
            DatatypeConstants.FIELD_UNDEFINED );
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }

    private static LocalDateTime xmlGregorianCalendarToLocalDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    Duration.ofMillis( xcal.getMillisecond() ).getNano()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }

    private Long reservationEntityUserId(ReservationEntity reservationEntity) {
        if ( reservationEntity == null ) {
            return null;
        }
        UserEntity user = reservationEntity.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String reservationEntityBookVersionIsbn(ReservationEntity reservationEntity) {
        if ( reservationEntity == null ) {
            return null;
        }
        BookVersionEntity bookVersion = reservationEntity.getBookVersion();
        if ( bookVersion == null ) {
            return null;
        }
        String isbn = bookVersion.getIsbn();
        if ( isbn == null ) {
            return null;
        }
        return isbn;
    }

    protected UserEntity reservationToUserEntity(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( reservation.getUserId() );

        return userEntity;
    }

    protected BookVersionEntity reservationToBookVersionEntity(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        BookVersionEntity bookVersionEntity = new BookVersionEntity();

        bookVersionEntity.setIsbn( reservation.getBookVersion() );

        return bookVersionEntity;
    }

    protected void reservationToUserEntity1(Reservation reservation, UserEntity mappingTarget) {
        if ( reservation == null ) {
            return;
        }

        mappingTarget.setId( reservation.getUserId() );
    }

    protected void reservationToBookVersionEntity1(Reservation reservation, BookVersionEntity mappingTarget) {
        if ( reservation == null ) {
            return;
        }

        mappingTarget.setIsbn( reservation.getBookVersion() );
    }
}
