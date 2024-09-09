package com.example.medichart.predict.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PredictData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // userID 추가예정
    private String username;
    private Integer age; // Integer로 변경
    private Double alt; // Double로 변경
    private Double ast; // Double로 변경
    private Double bmi; // Double로 변경
    private Integer diastolicBloodPressure; // Integer로 변경
    private Integer drink; // Integer로 변경
    private Double fastingBloodGlucose; // Double로 변경
    private Double ggtp; // Double로 변경
    private Double hemoglobin; // Double로 변경
    private Double proteinuria; // Double로 변경
    private Double serumCreatinine; // Double로 변경
    private Integer sex; // Integer로 변경
    private Integer smoke; // Integer로 변경
    private Integer systolicBloodPressure; // Integer로 변경
    private Double waist; // Double로 변경

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
