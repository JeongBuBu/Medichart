package com.example.medichart.translator.api;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TranslateService {

    private static Translate getTranslateService() throws IOException {
        InputStream credentialsStream = TranslateService.class.getResourceAsStream("/medichart-428805-42763aa8030c.json");
        if (credentialsStream == null) {
            throw new IOException("Resource not found: /medichart-428805-42763aa8030c.json");
        }
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        return TranslateOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }

    public static String translateText(String text, String targetLanguage) {
        try {
            Translate translate = getTranslateService();

            // 텍스트 전처리 및 문장 분할
            List<String> sentences = Arrays.asList(text.split("(?<=\\.)|(?<=\\n)|(?<=\\r)"));
            List<String> translatedSentences = sentences.stream()
                    .map(sentence -> {
                        Translation translation = translate.translate(
                                sentence.trim(),
                                Translate.TranslateOption.targetLanguage(targetLanguage)
                        );
                        return translation.getTranslatedText();
                    })
                    .collect(Collectors.toList());

            return String.join(" ", translatedSentences);
        } catch (IOException e) {
            e.printStackTrace();
            return "번역 서비스에 접근할 수 없습니다: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "번역에 실패했습니다: " + e.getMessage();
        }
    }
}
