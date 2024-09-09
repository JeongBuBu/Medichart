package com.example.medichart.predict.controller;

import com.example.medichart.predict.entity.PredictResult;
import com.example.medichart.predict.service.PredictResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
@CrossOrigin(origins = "http://localhost:3000")
public class PredictResultSendController {

    private static final Logger logger = LoggerFactory.getLogger(PredictResultSendController.class);
    private final PredictResultService predictResultService;

    @Autowired
    public PredictResultSendController(PredictResultService predictResultService) {
        this.predictResultService = predictResultService;
    }

    @GetMapping
    public ResponseEntity<List<PredictResult>> getPredictionResultsByUserId(@RequestParam String username) {
        List<PredictResult> results = predictResultService.getPredictResultsByUsername(username);
        if (results == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(results); // 200 OK
    }

}
