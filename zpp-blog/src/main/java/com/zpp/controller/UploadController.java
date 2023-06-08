package com.zpp.controller;

import com.zpp.annotation.SystemLog;
import com.zpp.domain.ResponseResult;
import com.zpp.domain.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "头像上传接口")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    //@SystemLog(businessName = "头像更改") 有bug
    @PostMapping("/upload")
    @ApiOperation(value = "头像更改",notes = "用户修改头像上传七牛云")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "img",value = "头像图片，支持jpg,png",dataTypeClass = MultipartFile.class,required = true)
    })
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}