package com.example.medichart.predict.repository;

import com.example.medichart.predict.entity.PredictData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictRepository extends JpaRepository<PredictData, Long> {
}
