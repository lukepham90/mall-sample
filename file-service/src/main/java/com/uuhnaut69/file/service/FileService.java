package com.uuhnaut69.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author uuhnaut
 * @project mall
 */
public interface FileService {

  String uploadToAwsS3(MultipartFile multipartFile);

  void deleteFileInAwsS3(String s3Path);
}
