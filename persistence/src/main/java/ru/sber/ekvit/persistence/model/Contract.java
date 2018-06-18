package ru.sber.ekvit.persistence.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Contract extends BaseEntity {

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractor_id")
    private Contractor contractor;

    @NonNull
    @Column(name = "numb")
    private String numb;

    @NonNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NonNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "end_date")
    private LocalDate endDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_encoding")
    private FileEncoding fileEncoding;

    @NonNull
    @Column(name = "delimiter")
    private String delimiter;

    public Contract(Integer id, String name, Contractor contractor, String numb, LocalDate startDate, LocalDate endDate, FileType fileType, FileEncoding fileEncoding, String delimiter) {
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

    public Contract(Contract contract) {
        this(contract.getId(), contract.getName(), contract.getContractor(), contract.getNumb(), contract.getStartDate(), contract.getEndDate(), contract.getFileType(), contract.getFileEncoding(), contract.getDelimiter());
    }

}
