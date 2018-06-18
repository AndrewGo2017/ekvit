package ru.sber.ekvit.persistence.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "application_events")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApplicationEvent extends BaseEntity {
    @NonNull
    @Column(name = "date")
    private LocalDateTime date;

    @NonNull
    @Column(name = "source")
    private String source;

    @NonNull
    @Column(name = "event")
    private String event;

    @NonNull
    @Column(name = "status")
    private ApplicationStatus status;

    public ApplicationEvent(Integer id, LocalDateTime date, String source, String event, ApplicationStatus status) {
        super(id);
        this.date = date;
        this.source = source;
        this.event = event;
        this.status = status;
    }

    public ApplicationEvent(ApplicationEvent applicationEvent){
        this(applicationEvent.getId(), applicationEvent.getDate(), applicationEvent.getSource(), applicationEvent.getEvent(), applicationEvent.getStatus());
    }
}
