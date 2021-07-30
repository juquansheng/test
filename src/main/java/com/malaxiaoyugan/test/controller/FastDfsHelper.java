package com.malaxiaoyugan.test.controller;


import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class FastDfsHelper {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;


    /**
     * 文件上传, byte 流类型
     *
     * @param bytes     文件字节
     * @param fileSize  文件大小
     * @param extension 文件扩展名
     * @return fastDfs路径
     */
    public String uploadFile(byte[] bytes,String fileName, long fileSize, String extension) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        StorePath storePath = fastFileStorageClient.uploadFile(byteArrayInputStream, fileSize, extension, null);
        System.out.println(storePath.getGroup() + "===" + storePath.getPath() + "======" + storePath.getFullPath());
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }

    /**
     * MultipartFile类型的文件上传ַ
     *
     * @param file 文件
     * @return 文件url
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = file.getName();
        long fileSize = file.getSize();
        return this.uploadFile(bytes,fileName,fileSize,extension);
    }

    /**
     * 带输入流形式的文件上传
     *
     * @param inputStream 文件流
     * @param size 大小
     * @param fileName 文件名
     * @return 文件url
     */
    public String uploadFile(InputStream inputStream, long size, String fileName) {
        StorePath path = fastFileStorageClient.uploadFile(inputStream, size, fileName, null);
        String fileUrl = getResAccessUrl(path);
        return fileUrl;
    }

    /**
     * 将一段文本文件写到fastdfs的服务器上
     *
     * @param content 文本内容
     * @param fileExtension 文件扩展名
     * @return 文件url
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath path = fastFileStorageClient.uploadFile(stream, buff.length, fileExtension, null);
        String fileUrl = getResAccessUrl(path);
        return fileUrl;
    }

    /**
     * 下载文件
     *
     * @param fileUrl 文件URL
     * @return 文件字节
     */
    public byte[] downloadFile(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        return fastFileStorageClient.downloadFile(group, path, downloadByteArray);
    }

    /**
     * 下载文件
     * @param fileUrl 文件url
     * @param response 响应
     */
    public void downloadFile(String fileUrl, HttpServletResponse response) throws UnsupportedEncodingException {

        byte[] bytes = this.downloadFile(fileUrl);
        // 这里只是为了整合fastdfs，所以写死了文件格式。需要在上传的时候保存文件名。下载的时候使用对应的格式  attachment 打开下载框 inline 页面内打开
        response.setHeader("Content-disposition", "inline;filename=" + URLEncoder.encode("fileName", "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteFile(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 封装文件完整URL地址
     */
    public String getResAccessUrl(StorePath storePath) {
        return storePath.getFullPath();
    }


}