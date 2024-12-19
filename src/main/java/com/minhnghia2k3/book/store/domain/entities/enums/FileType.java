package com.minhnghia2k3.book.store.domain.entities.enums;

import org.springframework.http.MediaType;

import java.util.Arrays;

public enum FileType {
    JPG("jpg", MediaType.IMAGE_JPEG),
    JPEG("jpeg", MediaType.IMAGE_JPEG),
    TXT("txt", MediaType.TEXT_PLAIN),
    PNG("png", MediaType.IMAGE_PNG),
    PDF("pdf", MediaType.APPLICATION_PDF);

    private final String extension;

    private final MediaType mediaType;

    FileType(String extension, MediaType mediaType) {
        this.extension = extension;
        this.mediaType = mediaType;
    }

    /**
     * Method extracts file extension from file name and sanitize file extension.
     * @param file String
     * @return MediaType
     */
    public static MediaType fromFileName(String file) {
        var dotIndex = file.lastIndexOf(".");
        var fileExtension = (dotIndex == -1) ? "" : file.substring(dotIndex + 1);

        // Finding matching enum constant for the file extension
        return Arrays.stream(values())
                .filter(e -> e.getExtension().equals(fileExtension))
                .findFirst()
                .map(FileType::getMediaType)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
    }

    public String getExtension() {
        return extension;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
