package ru.sber.ekvit.persistence.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.sber.ekvit.persistence.model.Contract;
import ru.sber.ekvit.persistence.model.FileEncoding;
import ru.sber.ekvit.persistence.model.FileType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContractTo extends BaseTo {
    private String name;

    private Integer contractor;

    private String numb;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;

    private FileType fileType;

    private FileEncoding fileEncoding;

    private String delimiter;

    public ContractTo(Integer id, String name, Integer contractor, String numb, LocalDate startDate, LocalDate endDate, FileType fileType, FileEncoding fileEncoding, String delimiter ) {
        super(id);
        this.name = name;
        this.contractor = contractor;
        this.numb = numb;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fileType = fileType;
        this.fileEncoding = fileEncoding;
        this.delimiter = delimiter;
    }

    public ContractTo(Contract contract){
        this(contract.getId(), contract.getName(),contract.getContractor().getId(),contract.getNumb(), contract.getStartDate(), contract.getEndDate(), contract.getFileType(), contract.getFileEncoding(), contract.getDelimiter());
    }
}
