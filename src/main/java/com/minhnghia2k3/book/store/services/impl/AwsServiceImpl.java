package com.minhnghia2k3.book.store.services.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.kafka.model.S3;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.minhnghia2k3.book.store.services.AwsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AwsServiceImpl implements AwsService {
    private static final Logger log = LoggerFactory.getLogger(AwsServiceImpl.class);
    private final AmazonS3 s3Client;

    public AwsServiceImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    // Method to upload a file to an S3 bucket
    @Override
    public void uploadFile(
            final String bucketName,
            final String keyName,
            final Long contentLength,
            final String contentType,
            final InputStream value
    ) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType(contentType);
        String fileName = generateFileName(keyName);

        s3Client.putObject(bucketName, fileName, value, metadata);
        log.info("File upload to bucket({}): {}", bucketName, fileName);
    }

    private String generateFileName(String keyName) {
        return new Date().getTime() + "-" +  keyName.replace(" ", "_");
    }

    // Method to download a file from an S3 bucket
    @Override
    public ByteArrayOutputStream downloadFile(
            final String bucketName,
            final String keyName
    ) throws IOException {
        S3Object s3Object = s3Client.getObject(bucketName, keyName);
        InputStream inputStream = s3Object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int len;
        byte[] buffer = new byte[4096];
        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        log.info("File downloaded from bucket({}): {}", bucketName, keyName);
        return outputStream;
    }

    // Method to list files in an S3 bucket
    @Override
    public List<String> listFiles(final String bucketName) throws AmazonClientException {
        List<String> keys = new ArrayList<>();

        ObjectListing objectListing = s3Client.listObjects(bucketName);

        while (true) {
            List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            if (objectSummaries.isEmpty()) {
                break;
            }

            objectSummaries.stream()
                    .filter(item -> !item.getKey().endsWith("/"))
                    .map(S3ObjectSummary::getKey)
                    .forEach(keys::add);

            objectListing = s3Client.listNextBatchOfObjects(objectListing);
        }

        log.info("Files found in bucket({}): {}", bucketName, keys);
        return keys;
    }

    // Method to delete a file from an S3 bucket
    @Override
    public void deleteFile(
            final String bucketName,
            final String keyName
    ) {
        s3Client.deleteObject(bucketName, keyName);
        log.info("File deleted from bucket({}): {}", bucketName, keyName);
    }

}
