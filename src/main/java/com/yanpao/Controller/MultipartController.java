package com.yanpao.Controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 使用MultipartFile上传文件
 * Content-Type：multipart/form-data
 * 文件放在body中
 */
@RestController
@RequestMapping("multipart")
public class MultipartController {

    /**
     * 上传单个文件
     */
    @PostMapping("/upload1")
    public String handleFormUpload(@RequestParam("name") String name,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            FileCopyUtils.copy(bytes, new File("E:/tmp/asdasdasd.jpg"));
            return "uploadSuccess";
        }
        return "uploadFailure";
    }

    /**
     * 上传多个文件
     */
    @PostMapping("/upload2")
    public String handleFormUpload(@RequestParam("name") String name,
                                   @RequestParam("file") List<MultipartFile> files) throws IOException {
        if (files.size()>0) {
            for(MultipartFile file:files) {
                byte[] bytes = file.getBytes();
                FileCopyUtils.copy(bytes, new File("E:/tmp/"+file.getOriginalFilename()));
            }
            return "uploadSuccess";
        }
        return "uploadFailure";
    }

    /**
     * 上传多个文件，每个文件可以带说明
     */
    @PostMapping("/upload3")
    public String handleFormUpload(@RequestParam("name") String name,
                                   @RequestParam Map<String, MultipartFile> filemap) throws IOException {
        if (filemap.size()>0) {
            for(Map.Entry<String,MultipartFile> entry:filemap.entrySet()) {
                byte[] bytes = entry.getValue().getBytes();
                FileCopyUtils.copy(bytes, new File("E:/tmp/"+entry.getValue().getOriginalFilename()));
            }
            return "uploadSuccess";
        }
        return "uploadFailure";
    }

}
