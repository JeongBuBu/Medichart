package com.example.medichart.predict.service;

import com.example.medichart.predict.entity.PredictResult;
import com.example.medichart.predict.repository.PredictResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictResultService {

    private final PredictResultRepository predictResultRepository;

    @Autowired
    public PredictResultService(PredictResultRepository predictResultRepository) {
        this.predictResultRepository = predictResultRepository;
    }

    public PredictResult savePredictResult(PredictResult predictResult) {
        try {
            return predictResultRepository.save(predictResult);
        } catch (Exception e) {
            throw new RuntimeException("Error saving PredictResult", e);
        }
    }

    public List<PredictResult> getPredictResultsByUsername(String username) {
        return predictResultRepository.findByUsername(username);
    }
}
