package ru.sber.ekvit.persistence.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "constants")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Constant extends BaseEntity {
    @NonNull
    @Column(name = "main_path")
    private String mainPath;
}
