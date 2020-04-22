package com.tis.service;

import com.tis.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString().substring(0, 12);
        String fileExtName = FileUtil.getFileExtName(file.getOriginalFilename());
        newFileName += fileExtName;
        file.transferTo(new File(uploadFolder,newFileName));
        Map<String, String> result = new HashMap<String,String>();
        result.put("type","success");
        result.put("filename",staticAccessPath.substring(0,staticAccessPath.length()-2) + newFileName);
        result.put("originalFilename",originalFilename);
        return result;
    }
}
