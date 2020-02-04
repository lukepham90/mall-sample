package com.uuhnaut69.mall.file.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.uuhnaut69.mall.core.utils.TimeUtils;
import com.uuhnaut69.mall.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final String amazonS3EndPoint;

    private final String amazonS3BucketName;

    private AmazonS3 amazonS3Client;

    public FileServiceImpl(String amazonS3EndPoint, String amazonS3BucketName, AmazonS3 amazonS3Client) {
        this.amazonS3EndPoint = amazonS3EndPoint;
        this.amazonS3BucketName = amazonS3BucketName;
        this.amazonS3Client = amazonS3Client;
    }

    @Override
    public String uploadToAwsS3(MultipartFile multipartFile) throws Exception {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = amazonS3EndPoint + "/" + amazonS3BucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return fileUrl;
    }

    @Override
    public void deleteFileInAwsS3(String s3Path) throws Exception {
        String fileName = s3Path.substring(s3Path.lastIndexOf('/') + 1);
        amazonS3Client.deleteObject(new DeleteObjectRequest(amazonS3BucketName, fileName));
    }

    /**
     * Upload to s3
     *
     * @param fileName
     * @param file
     */
    private void uploadFileTos3bucket(String fileName, File file) {
        amazonS3Client.putObject(new PutObjectRequest(amazonS3BucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    /**
     * Convert multipart into file
     *
     * @param file
     * @return File
     * @throws IOException
     */
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        try {
            fos.write(file.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            fos.close();
        }

        return convFile;
    }

    /**
     * Generate file name
     *
     * @param multiPart
     * @return file name
     */
    private String generateFileName(MultipartFile multiPart) {
        return TimeUtils.getCurrentTimestamp() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
