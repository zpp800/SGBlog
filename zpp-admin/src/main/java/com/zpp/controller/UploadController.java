package com.zpp.controller;

import com.zpp.domain.ResponseResult;
import com.zpp.domain.service.UploadService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation(value = "文章缩略图",notes = "文章缩略图与文章内容图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "img",value = "文章缩略图，支持jpg,png",dataTypeClass = MultipartFile.class,required = true)
    })
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {
        try {
            return uploadService.uploadImg(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传上传失败");
        }
    }
}