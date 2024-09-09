package com.example.medichart.translator.api;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.protobuf.ByteString;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Vision {
    public static String extractTextFromImage(InputStream imageInputStream) throws IOException {
        byte[] data = imageInputStream.readAllBytes();
        ByteString imgBytes = ByteString.copyFrom(data);

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        List<AnnotateImageRequest> requests = new ArrayList<>();
        requests.add(request);

        try (InputStream credentialsStream = Vision.class.getResourceAsStream("/medichart-428805-42763aa8030c.json")) {
            if (credentialsStream == null) {
                throw new IOException("Resource not found: /medichart-428805-42763aa8030c.json");
            }
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
            ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();

            try (ImageAnnotatorClient client = ImageAnnotatorClient.create(settings)) {
                List<AnnotateImageResponse> responses = client.batchAnnotateImages(requests).getResponsesList();
                for (AnnotateImageResponse res : responses) {
                    for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                        return annotation.getDescription();
                    }
                }
            }
        }
        return "";
    }

}