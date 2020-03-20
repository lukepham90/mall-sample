package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author uuhnaut
 * @project mall
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
@Api(tags = "File", value = "File Endpoint")
public class FileController {

    private final FileService fileService;

    /**
     * Upload file
     *
     * @param multipartFile {@link MultipartFile}
     * @return GenericResponse
     */
    @ApiOperation(value = "File Endpoint", notes = "Admin endpoint")
    @PostMapping(path = UrlConstants.ADMIN_URL + UrlConstants.FILE_URL)
    public GenericResponse upload(@RequestPart(value = "file") MultipartFile multipartFile) {
        fileService.uploadToAwsS3(multipartFile);
        return GenericResponse.builder().data(fileService.uploadToAwsS3(multipartFile)).build();
    }

    /**
     * Delete file
     *
     * @param s3Path Url file on s3 bucket
     * @return GenericResponse
     */
    @ApiOperation(value = "File Endpoint", notes = "Admin endpoint")
    @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.FILE_URL)
    public GenericResponse delete(@RequestPart(value = "s3Path") String s3Path) {
        fileService.deleteFileInAwsS3(s3Path);
        return new GenericResponse();
    }

}
