package com.example.medichart.predict.controller;

import com.example.medichart.predict.entity.PredictData;
import com.example.medichart.predict.entity.PredictResult;
import com.example.medichart.predict.dto.PredictDataRequest; // 데이터 요청 DTO
import com.example.medichart.predict.service.PredictDataService;
import com.example.medichart.predict.service.PredictResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/predict-data")
@CrossOrigin(origins = "http://localhost:3000") // 프론트엔드의 주소
public class PredictDataController {

    private final PredictDataService predictDataService;
    private final PredictResultService predictResultService;
    private static final Logger logger = LoggerFactory.getLogger(PredictDataController.class);

    @Autowired
    public PredictDataController(PredictDataService predictDataService, PredictResultService predictResultService) {
        this.predictDataService = predictDataService;
        this.predictResultService = predictResultService;
    }

    @PostMapping
    public ResponseEntity<String> createPredictData(@RequestBody PredictDataRequest predictDataRequest) {
        logger.info("Received request: {}", predictDataRequest);
        try {
            // 데이터 유효성 검사
            if (predictDataRequest.getAge() == null || predictDataRequest.getAge() < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid age");
            }

            // Python 서버 URL
            String pythonServerUrl = "http://localhost:5000/predict";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 각 모델별로 필요한 데이터만 포함된 JSON 생성
            String[] models = {"diabetes_model", "heart_disease_model", "hypertension_model", "kidney_disease_model", "obesity_model", "stroke_prediction_model"};
            Map<String, Object> responseMap = new HashMap<>();

            for (String modelName : models) {
                Map<String, Object> modelRequest = new HashMap<>();
                switch (modelName) {
                    case "diabetes_model":
                        modelRequest.put("age", predictDataRequest.getAge());
                        modelRequest.put("waist", predictDataRequest.getWaist());
                        modelRequest.put("BMI", predictDataRequest.getBmi());
                        modelRequest.put("systolic blood pressure", predictDataRequest.getSystolicBloodPressure());
                        modelRequest.put("γ-GTP", predictDataRequest.getGgtp());
                        modelRequest.put("ALT", predictDataRequest.getAlt());
                        break;
                    case "heart_disease_model":
                        modelRequest.put("systolic blood pressure", predictDataRequest.getSystolicBloodPressure());
                        modelRequest.put("diastolic blood pressure", predictDataRequest.getDiastolicBloodPressure());
                        modelRequest.put("FBG", predictDataRequest.getFastingBloodGlucose());
                        modelRequest.put("creatinine", predictDataRequest.getSerumCreatinine());
                        modelRequest.put("hemoglobin", predictDataRequest.getHemoglobin());
                        modelRequest.put("waist", predictDataRequest.getWaist());
                        modelRequest.put("BMI", predictDataRequest.getBmi());
                        modelRequest.put("smoke", predictDataRequest.getSmoke());
                        modelRequest.put("drink", predictDataRequest.getDrink());
                        break;
                    case "hypertension_model":
                        modelRequest.put("age", predictDataRequest.getAge());
                        modelRequest.put("waist", predictDataRequest.getWaist());
                        modelRequest.put("BMI", predictDataRequest.getBmi());
                        modelRequest.put("systolic blood pressure", predictDataRequest.getSystolicBloodPressure());
                        modelRequest.put("diastolic blood pressure", predictDataRequest.getDiastolicBloodPressure());
                        break;
                    case "kidney_disease_model":
                        modelRequest.put("creatinine", predictDataRequest.getSerumCreatinine());
                        modelRequest.put("hemoglobin", predictDataRequest.getHemoglobin());
                        modelRequest.put("FBG", predictDataRequest.getFastingBloodGlucose());
                        modelRequest.put("smoke", predictDataRequest.getSmoke());
                        break;
                    case "obesity_model":
                        modelRequest.put("waist", predictDataRequest.getWaist());
                        modelRequest.put("BMI", predictDataRequest.getBmi());
                        modelRequest.put("systolic blood pressure", predictDataRequest.getSystolicBloodPressure());
                        modelRequest.put("diastolic blood pressure", predictDataRequest.getDiastolicBloodPressure());
                        break;
                    case "stroke_prediction_model":
                        modelRequest.put("age", predictDataRequest.getAge());
                        modelRequest.put("waist", predictDataRequest.getWaist());
                        modelRequest.put("systolic blood pressure", predictDataRequest.getSystolicBloodPressure());
                        modelRequest.put("diastolic blood pressure", predictDataRequest.getDiastolicBloodPressure());
                        modelRequest.put("creatinine", predictDataRequest.getSerumCreatinine());
                        modelRequest.put("FBG", predictDataRequest.getFastingBloodGlucose());
                        modelRequest.put("hemoglobin", predictDataRequest.getHemoglobin());
                        modelRequest.put("smoke", predictDataRequest.getSmoke());
                        modelRequest.put("drink", predictDataRequest.getDrink());
                        modelRequest.put("BMI", predictDataRequest.getBmi());
                        break;
                    default:
                        break;
                }
                // 요청 본문 구성
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("data", Map.of(modelName, modelRequest)); // 모델 이름을 키로 사용하는 데이터
                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
                ResponseEntity<Map> response = restTemplate.exchange(pythonServerUrl, HttpMethod.POST, requestEntity, Map.class);
                responseMap.put(modelName, response.getBody());
            }

            // 파이썬에서 받아오기
            logger.info("Received predictions: {}", responseMap);

            // 예측된 확률만 추출하여 PredictResult 객체에 저장
            PredictResult predictResult = new PredictResult();
            for (String modelName : responseMap.keySet()) {
                Map<String, Object> modelResponse = (Map<String, Object>) responseMap.get(modelName);
                logger.info("Model Name: {}", modelName);
                logger.info("Model Response: {}", modelResponse);

                // Extract nested data
                Map<String, Object> innerModelResponse = (Map<String, Object>) modelResponse.get(modelName);
                logger.info("Inner Model Response: {}", innerModelResponse);

                Map<String, Object> probability = (Map<String, Object>) innerModelResponse.get("probability");
                logger.info("Probability Data: {}", probability);

                if (probability != null && probability.containsKey("disease")) {
                    double diseaseProbability = (Double) probability.get("disease");
                    logger.info("Model: {}, Disease Probability: {}", modelName, diseaseProbability);

                    // Update PredictResult object
                    switch (modelName) {
                        case "diabetes_model":
                            predictResult.setDiabetes(diseaseProbability);
                            break;
                        case "obesity_model":
                            predictResult.setObesity(diseaseProbability);
                            break;
                        case "heart_disease_model":
                            predictResult.setHeartDisease(diseaseProbability);
                            break;
                        case "stroke_prediction_model":
                            predictResult.setStroke(diseaseProbability);
                            break;
                        case "hypertension_model":
                            predictResult.setHypertension(diseaseProbability);
                            break;
                        case "kidney_disease_model":
                            predictResult.setKidney(diseaseProbability);
                            break;
                        default:
                            break;
                    }
                } else {
                    logger.warn("Model: {} returned no probability", modelName);
                }
            }
            // PredictResult에 username 설정
            predictResult.setUsername(predictDataRequest.getUsername());

            // PredictData 객체 생성 및 저장
            PredictData predictData = new PredictData();
            predictData.setUsername(predictDataRequest.getUsername()); // username을 UserID로 사용
            predictData.setAge(predictDataRequest.getAge());
            predictData.setSex(predictDataRequest.getSex());
            predictData.setBmi(predictDataRequest.getBmi());
            predictData.setWaist(predictDataRequest.getWaist());
            predictData.setSystolicBloodPressure(predictDataRequest.getSystolicBloodPressure());
            predictData.setDiastolicBloodPressure(predictDataRequest.getDiastolicBloodPressure());
            predictData.setFastingBloodGlucose(predictDataRequest.getFastingBloodGlucose());
            predictData.setAst(predictDataRequest.getAst());
            predictData.setAlt(predictDataRequest.getAlt());
            predictData.setGgtp(predictDataRequest.getGgtp());
            predictData.setSerumCreatinine(predictDataRequest.getSerumCreatinine());
            predictData.setHemoglobin(predictDataRequest.getHemoglobin());
            predictData.setSmoke(predictDataRequest.getSmoke());
            predictData.setDrink(predictDataRequest.getDrink());
            predictData.setProteinuria(predictDataRequest.getProteinuria());
            predictData.setUsername(predictDataRequest.getUsername()); // username 설정

            // PredictData 및 PredictResult 객체 저장
            predictDataService.savePredictData(predictData);
            predictResultService.savePredictResult(predictResult);

            return ResponseEntity.ok("PredictData and PredictResult processed and saved successfully");

        } catch (Exception e) {
            logger.error("Error occurred while processing prediction: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }


}
