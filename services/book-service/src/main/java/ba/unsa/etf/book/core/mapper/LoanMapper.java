package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.Loan;
import ba.unsa.etf.book.dao.model.LoanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(source = "bookVersion.isbn", target = "bookVersion")
    Loan entityToDto(LoanEntity loanEntity);

    @Mapping(source = "bookVersion", target = "bookVersion.isbn")
    LoanEntity dtoToEntity(Loan loan);

    @Mapping(source = "bookVersion", target = "bookVersion.isbn")
    void updateEntity(Loan loan, @MappingTarget LoanEntity loanEntity);
}
