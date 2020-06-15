package com.uuhnaut69.file.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.uuhnaut69.common.utils.TimeUtils;
import com.uuhnaut69.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  @Value("${amazon.s3.bucket-name}")
  private String amazonS3BucketName;

  @Value("${amazon.s3.endpoint}")
  private String amazonS3EndPoint;

  private final AmazonS3 amazonS3Client;

  @Override
  public String uploadToAwsS3(MultipartFile multipartFile) {
    String fileUrl = "";
    try {
      File file = convertMultiPartToFile(multipartFile);
      String fileName = generateFileName(multipartFile);
      fileUrl = amazonS3EndPoint + "/" + amazonS3BucketName + "/" + fileName;
      log.debug("Request to upload file in AWS has path {}", fileName);
      uploadFileTos3bucket(fileName, file);
      Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return fileUrl;
  }

  @Override
  public void deleteFileInAwsS3(String s3Path) {
    String fileName = s3Path.substring(s3Path.lastIndexOf('/') + 1);
    log.debug("Request to delete file in AWS has path {}", fileName);
    amazonS3Client.deleteObject(new DeleteObjectRequest(amazonS3BucketName, fileName));
  }

  private void uploadFileTos3bucket(String fileName, File file) {
    amazonS3Client.putObject(
        new PutObjectRequest(amazonS3BucketName, fileName, file)
            .withCannedAcl(CannedAccessControlList.PublicRead));
  }

  private File convertMultiPartToFile(MultipartFile file) {
    File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    try (FileOutputStream fos = new FileOutputStream(convertFile)) {
      fos.write(file.getBytes());
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return convertFile;
  }

  private String generateFileName(MultipartFile multiPart) {
    return TimeUtils.getCurrentTimestamp()
        + "_"
        + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
  }
}
