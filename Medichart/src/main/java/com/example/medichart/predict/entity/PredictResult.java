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
public class PredictResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private double diabetes; // 당뇨병 확률
    private double obesity; // 비만 확률
    private double heartDisease; // 심장병 확률
    private double stroke; // 뇌졸중 확률
    private double hypertension; // 고혈압 확률
    private double kidney; // 신장질환 확률
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
