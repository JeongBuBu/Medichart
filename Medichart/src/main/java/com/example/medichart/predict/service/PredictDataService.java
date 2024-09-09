package com.example.medichart.predict.service;

import com.example.medichart.predict.entity.PredictData;
import com.example.medichart.predict.repository.PredictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredictDataService {

    private final PredictRepository predictRepository;

    @Autowired
    public PredictDataService(PredictRepository predictRepository) {
        this.predictRepository = predictRepository;
    }

    public PredictData savePredictData(PredictData predictData) {
        try {
            return predictRepository.save(predictData);
        } catch (Exception e) {
            throw new RuntimeException("Error saving PredictData", e);
        }
    }

    public Optional<PredictData> getPredictDataById(Long id) {
        return predictRepository.findById(id); // Optional로 반환
    }
    public List<PredictData> getAllPredictData() {
        return predictRepository.findAll();
    }

}
