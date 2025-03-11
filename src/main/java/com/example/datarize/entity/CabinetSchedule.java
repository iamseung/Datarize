package com.example.datarize.entity;

import com.example.datarize.dto.CreateScheduleResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// 회의실 일정 도메인
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class CabinetSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;
    private String scheduleName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // 회의실
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "cabinet_id")
    private Cabinet cabinet;

    // 참여자
    @OneToMany
    private Set<User> participants = new HashSet<>();

    public CabinetSchedule(String scheduleName, LocalDateTime startTime, LocalDateTime endTime, Cabinet cabinet, Set<User> participants) {
        this.scheduleName = scheduleName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cabinet = cabinet;
        this.participants = participants;
    }

    public CabinetSchedule of(String scheduleName, LocalDateTime startTime, LocalDateTime endTime, Cabinet cabinet, Set<User> participants) {
        return new CabinetSchedule(scheduleName, startTime, endTime, cabinet, participants);
    }

    public CabinetSchedule of(String scheduleName, LocalDateTime startTime, LocalDateTime endTime, Cabinet cabinet) {
        return new CabinetSchedule(scheduleName, startTime, endTime, cabinet, null);
    }

    public CreateScheduleResponseDto toCreateResponseDto() {
        return new CreateScheduleResponseDto(
                id,
                scheduleName,
                startTime,
                endTime,
                cabinet.getId(),
                participants.stream().map(user -> user.getId()).collect(Collectors.toSet())
        );
    }

    public ScheduleResponseDto toScheduleResponseDto() {
        return new ScheduleResponseDto(
                id,
                scheduleName,
                startTime,
                endTime,
                cabinet.toDto(),
                participants.stream().map(User::toDto).collect(Collectors.toList())
        );
    }
}
