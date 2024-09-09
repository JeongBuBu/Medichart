package com.example.medichart.translator;

import com.example.medichart.translator.api.TranslateService;
import com.example.medichart.translator.api.Vision;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path to your image: ");
        String imagePath = scanner.nextLine();

        String targetLanguage = "en";

        try (InputStream imageInputStream = new FileInputStream(imagePath)) {
            String extractedText = Vision.extractTextFromImage(imageInputStream);
            System.out.println("Extracted Text: " + extractedText);

            String translatedText = TranslateService.translateText(extractedText, targetLanguage);
            System.out.println("Translated Text: " + translatedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
