package com.van.mall.service.serviceImpl;

import com.google.common.collect.Lists;
import com.van.mall.service.IFileService;
import com.van.mall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Van
 * @date 2020/3/11 - 19:25
 */
@Service
@Slf4j
public class FileServiceImpl implements IFileService {
    public  String  upload(MultipartFile file,String path){
        String fileName=file.getOriginalFilename();
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName= UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始上传文件，上传文件名{}，上传路径{}，新文件名{}",fileName,path,fileExtensionName);
        File fileDir=new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile=new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
            //file upload successful

            // upload targetFile to my FTP server
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            //  delete the file in upload folder after uploaded
            targetFile.delete();
        } catch (IOException e) {
            log.error("文件上传异常",e);
            return null;
        }
        return targetFile.getName();
    }
}
