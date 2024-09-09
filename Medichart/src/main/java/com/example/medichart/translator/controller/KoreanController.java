package com.example.medichart.translator.controller;

import com.example.medichart.translator.api.TranslateService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Korean")
@CrossOrigin(origins = "http://localhost:3000")
public class KoreanController {

    @PostMapping("/translate")
    public Map<String, String> translateText(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        Map<String, String> response = new HashMap<>();

        if (text == null || text.isEmpty()) {
            response.put("message", "텍스트가 제공되지 않았습니다.");
            return response;
        }

        try {
            // 한국어로 번역
            String translatedText = TranslateService.translateText(text, "ko");

            response.put("translatedText", translatedText);
            response.put("message", "번역 성공");

        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "번역 실패: " + e.getMessage());
        }

        return response;
    }
}
