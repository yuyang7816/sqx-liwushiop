package com.sqx.file.controller;


import com.sqx.common.core.domain.Result;
import com.sqx.common.file.domain.UploadRes;
import com.sqx.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chengming
 * @since 2025/4/29
 */
@Api(value = "文件")
@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "普通文件上传")
    @PostMapping("upload")
    public Result<UploadRes> upload(MultipartFile file) {
        return fileService.upload(file);
    }
}
