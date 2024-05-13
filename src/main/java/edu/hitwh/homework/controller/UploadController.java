package edu.hitwh.homework.controller;


import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.util.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {
    @Autowired
    private AliOSSUtils aliOSSUtils;
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        String url = aliOSSUtils.upload(file);
        return Result.success(url);
    }
}
