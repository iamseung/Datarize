package com.example.datarize.entity;

import com.example.datarize.dto.CabinetDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

// 회의실 도메인
@Getter
@Entity
@ToString
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinet_id")
    private Long id;
    private String name;
    private int capacity;

    // 회의실에 매핑된 스케쥴실
    @OneToMany(mappedBy = "cabinet", fetch = FetchType.LAZY)
    @OrderBy("startTime ASC")
    private Set<CabinetSchedule> schedules = new LinkedHashSet<>();

    public CabinetDto toDto() {
        return new CabinetDto(id, name, capacity);
    }
}
