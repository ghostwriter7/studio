package com.rozplochowski.studio.classmanagement.adapter.out.persistence;

import com.rozplochowski.studio.classmanagement.domain.Difficulty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dance_class",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_dance_class_activity", columnNames = "activity")
    }
)
public class DanceClassEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    private String activity;

    @Column(length = 2048)
    private String description;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;


}
