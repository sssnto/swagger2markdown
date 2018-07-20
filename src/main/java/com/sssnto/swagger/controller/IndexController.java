package com.sssnto.swagger.controller;

import com.sssnto.swagger.ZipCompress;
import com.sssnto.swagger.converter.JxiPlatformConverter;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "/templates/index.html";
    }


    @RequestMapping("/swagger2mardown")
    private ResponseEntity<byte[]> swagger2mardown(String url) throws Exception {
        try {
            String targetFile = "src/docs/markdown/generated.zip";
            String sourceDir = "src/docs/markdown/generated";


            new JxiPlatformConverter().converter(url,sourceDir);

            new ZipCompress(targetFile,sourceDir).zip();

            File file = new File(targetFile);
            return download( file.getName(),file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<byte[]> download(String fileName, File file) throws IOException {
        String dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

}
