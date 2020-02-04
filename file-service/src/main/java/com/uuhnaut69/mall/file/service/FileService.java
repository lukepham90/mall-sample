package com.uuhnaut69.mall.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * Upload file to amazon s3
     *
     * @param multipartFile
     * @return file path in s3
     * @throws Exception
     */
    String uploadToAwsS3(MultipartFile multipartFile) throws Exception;

    /**
     * Delete file in amazon S3
     *
     * @param s3Path
     * @throws Exception
     */
    void deleteFileInAwsS3(String s3Path) throws Exception;
}
