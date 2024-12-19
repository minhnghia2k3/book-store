package com.minhnghia2k3.book.store.controllers;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.minhnghia2k3.book.store.domain.entities.enums.FileType;
import com.minhnghia2k3.book.store.services.AwsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/s3")
public class AwsController {
    private final AwsService service;

    public AwsController(AwsService service) {
        this.service = service;
    }

    // Endpoint to list all files in bucket
    @GetMapping("/{bucketName}")
    public ResponseEntity<List<String>> listFiles(@PathVariable String bucketName) {
        List<String> body = service.listFiles(bucketName);

        return ResponseEntity.ok(body);
    }

    @PostMapping("/{bucketName}/upload")
    public ResponseEntity<?> uploadFile(
            @PathVariable("bucketName") String bucketName,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file.isEmpty()) {
            throw new FileNotFoundException("File is empty");
        }

        try (InputStream inputStream = file.getInputStream()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String contentType = file.getContentType();
            long fileSize = file.getSize();
            service.uploadFile(bucketName, fileName, fileSize, contentType, inputStream);
            return ResponseEntity.ok().body("File uploaded successfully");
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    // Endpoint to download file by specific bucket and file name.
    @GetMapping("/{bucketName}/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String bucketName, @PathVariable String fileName) {
        try {
            var body = service.downloadFile(bucketName, fileName);
            String header = "attachment; filename=%s".formatted(fileName);
            MediaType contentType = FileType.fromFileName(fileName);
            byte[] fileBody = body.toByteArray();
            return ResponseEntity.ok().header("Content-Disposition", header).contentType(contentType).body(fileBody);
        } catch (AmazonS3Exception e) {
            throw new AmazonS3Exception(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{bucketName}/{fileName}")
    public ResponseEntity<?> deleteFile(@PathVariable String bucketName, @PathVariable String fileName) {
        service.deleteFile(bucketName, fileName);
        return ResponseEntity.noContent().build();
    }
}
