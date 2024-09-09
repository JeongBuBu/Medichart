package com.example.medichart.translator.controller;

import com.example.medichart.translator.api.Vision;
import com.example.medichart.translator.api.TranslateService; // 추가된 부분
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageUploadController {

    @PostMapping("/upload")
    public Map<String, String> handleImageUpload(
            @RequestParam("image") MultipartFile image,
            @RequestParam("language") String language) {
        Map<String, String> response = new HashMap<>();

        if (image.isEmpty()) {
            response.put("message", "파일이 업로드되지 않았습니다.");
            return response;
        }

        try {
            // 이미지에서 텍스트 추출
            String extractedText = Vision.extractTextFromImage(image.getInputStream());

            // 텍스트 번역
            String translatedText = TranslateService.translateText(extractedText, language);

            // 응답 설정
            response.put("uploadedText", extractedText);
            response.put("translatedText", translatedText);
            response.put("message", "이미지 업로드 및 텍스트 추출 및 번역 성공");

        } catch (IOException e) {
            e.printStackTrace();
            response.put("message", "텍스트 추출 및 번역 실패: " + e.getMessage());
        }

        return response;
    }

}
