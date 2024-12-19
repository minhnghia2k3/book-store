package com.minhnghia2k3.book.store.services;

import com.amazonaws.AmazonClientException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface AwsService {
    void uploadFile(
            final String bucketName,
            final String keyName,
            final Long contentLength,
            final String contentType,
            final InputStream value
    ) throws IOException;

    ByteArrayOutputStream downloadFile(
            final String bucketName,
            final String keyName
    ) throws IOException;

    List<String> listFiles(final String bucketName) throws AmazonClientException;

    void deleteFile(
            final String bucketName,
            final String keyName
    );
}
