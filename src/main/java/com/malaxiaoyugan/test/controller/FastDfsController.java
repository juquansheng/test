package com.malaxiaoyugan.test.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@Api(tags = "文件控制器")
@RequestMapping("/v1/file")
public class FastDfsController {

    @Autowired
    private FastDfsHelper helper;



    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传文件")
    public String uploadFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String originalFileName = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = file.getName();
        long fileSize = file.getSize();
        System.out.println(originalFileName + "==========" + fileName + "===========" + fileSize + "===============" + extension + "====" + bytes.length);
        String s = helper.uploadFile(bytes, originalFileName, fileSize, extension);
        return s;
    }


    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping(value = "/upload1",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传文件1")
    public String uploadFile1(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String originalFileName = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = file.getName();
        long fileSize = file.getSize();
        //System.out.println(originalFileName + "==========" + fileName + "===========" + fileSize + "===============" + extension + "====" + bytes.length);
        //String s = helper.uploadFile(bytes, originalFileName, fileSize, extension);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        StorePath storePath = fastFileStorageClient.uploadFile(byteArrayInputStream, fileSize, extension, null);
        System.out.println(storePath.getGroup() + "===" + storePath.getPath() + "======" + storePath.getFullPath());
        String fileUrl = storePath.getFullPath();
        return fileUrl;

    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    @ApiOperation("下载文件")
    public void downloadFile(String fileUrl, HttpServletResponse response) throws IOException {
        helper.downloadFile(fileUrl,response);
    }

    @ApiOperation("查询文件路径")
    @RequestMapping(value = "/url",method = RequestMethod.GET)
    @ResponseBody
    public String getFilePath(String path,String group) {
        StorePath storePath = new StorePath();
        storePath.setPath(path);
        storePath.setGroup(group);
        return helper.getResAccessUrl(storePath);
    }

}
