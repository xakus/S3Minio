package org.pro_code.s3minio.service;

import io.minio.*;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private       String      bucketName;


    public ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            // Проверяем, существует ли бакет
            List<Bucket> buckets      = minioClient.listBuckets();
            boolean      bucketExists = buckets.stream().anyMatch(bucket -> bucket.name().equals(bucketName));

            // Если бакет не существует, создаем его
            if (!bucketExists) {
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
                minioClient.makeBucket(makeBucketArgs);
            }
            minioClient.putObject(PutObjectArgs.builder()
                                          .bucket(bucketName)
                                          .object(file.getOriginalFilename())
                                          .stream(file.getInputStream(), file.getSize(), -1)
                                          .contentType(file.getContentType())
                                          .build());
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<byte[]> downloadFile(String fileName) {
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                                                                .bucket(bucketName)
                                                                .object(fileName)
                                                                .build())) {
            return ResponseEntity.ok(stream.readAllBytes());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    public ResponseEntity<String> deleteFile(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                                             .bucket(bucketName)
                                             .object(fileName)
                                             .build());
            return ResponseEntity.ok("File delete successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
