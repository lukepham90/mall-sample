package com.uuhnaut69.mall.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author uuhnaut
 * @project mall
 */
public interface FileService {

    /**
     * Upload file to amazon s3
     *
     * @param multipartFile {@link MultipartFile}
     * @return file path in s3
     */
    String uploadToAwsS3(MultipartFile multipartFile);

    /**
     * Delete file in amazon S3
     *
     * @param s3Path Url file on S3 bucket
     */
    void deleteFileInAwsS3(String s3Path);
}
