package com.van.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Van
 * @date 2020/3/11 - 19:24
 */
public interface IFileService {
    String  upload(MultipartFile file, String path);
}
