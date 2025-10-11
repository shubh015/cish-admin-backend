// package com.example.demo.config;

// import io.minio.MakeBucketArgs;
// import io.minio.MinioClient;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;

// import io.minio.BucketExistsArgs;
// import jakarta.annotation.PostConstruct;
// import lombok.RequiredArgsConstructor;

// @Configuration
// @RequiredArgsConstructor
// public class MinioBucketInitializer {

//     private final MinioClient minioClient;

//     @Value("${minio.bucket.name}")
//     private String bucketName;

//     @PostConstruct
//     public void createBucketIfNotExists() {
//         try {
//             boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//             if (!found) {
//                 minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//                 System.out.println("✅ Created bucket: " + bucketName);
//             } else {
//                 System.out.println("✅ Bucket already exists: " + bucketName);
//             }
//         } catch (Exception e) {
//             throw new RuntimeException("Error while checking/creating MinIO bucket", e);
//         }
//     }
// }
