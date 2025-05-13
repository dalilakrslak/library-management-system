package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.Loan;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.model.LoanEntity;
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
public class LoanMapperImpl implements LoanMapper {

    private final DatatypeFactory datatypeFactory;

    public LoanMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public Loan entityToDto(LoanEntity loanEntity) {
        if ( loanEntity == null ) {
            return null;
        }

        Long userId = null;
        String bookVersion = null;
        Long id = null;
        LocalDateTime loanDate = null;
        LocalDateTime dueDate = null;
        LocalDateTime returnDate = null;

        userId = loanEntityUserId( loanEntity );
        bookVersion = loanEntityBookVersionIsbn( loanEntity );
        id = loanEntity.getId();
        loanDate = xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( loanEntity.getLoanDate() ) );
        dueDate = xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( loanEntity.getDueDate() ) );
        returnDate = xmlGregorianCalendarToLocalDateTime( localDateToXmlGregorianCalendar( loanEntity.getReturnDate() ) );

        Loan loan = new Loan( id, userId, bookVersion, loanDate, dueDate, returnDate );

        return loan;
    }

    @Override
    public LoanEntity dtoToEntity(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanEntity loanEntity = new LoanEntity();

        loanEntity.setUser( loanToUserEntity( loan ) );
        loanEntity.setBookVersion( loanToBookVersionEntity( loan ) );
        loanEntity.setId( loan.getId() );
        loanEntity.setLoanDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( loan.getLoanDate() ) ) );
        loanEntity.setDueDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( loan.getDueDate() ) ) );
        loanEntity.setReturnDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( loan.getReturnDate() ) ) );

        return loanEntity;
    }

    @Override
    public void updateEntity(Loan loan, LoanEntity loanEntity) {
        if ( loan == null ) {
            return;
        }

        if ( loanEntity.getUser() == null ) {
            loanEntity.setUser( new UserEntity() );
        }
        loanToUserEntity1( loan, loanEntity.getUser() );
        if ( loanEntity.getBookVersion() == null ) {
            loanEntity.setBookVersion( new BookVersionEntity() );
        }
        loanToBookVersionEntity1( loan, loanEntity.getBookVersion() );
        loanEntity.setId( loan.getId() );
        loanEntity.setLoanDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( loan.getLoanDate() ) ) );
        loanEntity.setDueDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( loan.getDueDate() ) ) );
        loanEntity.setReturnDate( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( loan.getReturnDate() ) ) );
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

    private Long loanEntityUserId(LoanEntity loanEntity) {
        if ( loanEntity == null ) {
            return null;
        }
        UserEntity user = loanEntity.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String loanEntityBookVersionIsbn(LoanEntity loanEntity) {
        if ( loanEntity == null ) {
            return null;
        }
        BookVersionEntity bookVersion = loanEntity.getBookVersion();
        if ( bookVersion == null ) {
            return null;
        }
        String isbn = bookVersion.getIsbn();
        if ( isbn == null ) {
            return null;
        }
        return isbn;
    }

    protected UserEntity loanToUserEntity(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( loan.getUserId() );

        return userEntity;
    }

    protected BookVersionEntity loanToBookVersionEntity(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        BookVersionEntity bookVersionEntity = new BookVersionEntity();

        bookVersionEntity.setIsbn( loan.getBookVersion() );

        return bookVersionEntity;
    }

    protected void loanToUserEntity1(Loan loan, UserEntity mappingTarget) {
        if ( loan == null ) {
            return;
        }

        mappingTarget.setId( loan.getUserId() );
    }

    protected void loanToBookVersionEntity1(Loan loan, BookVersionEntity mappingTarget) {
        if ( loan == null ) {
            return;
        }

        mappingTarget.setIsbn( loan.getBookVersion() );
    }
}
