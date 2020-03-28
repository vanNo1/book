package com.van.mall.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Van
 * @date 2020/3/11 - 21:49
 */
@Data
@Slf4j
public class FTPUtil {
    //    private static String ftpIp=PropertiesUtil.getPropertity("ftp.server.ip");
//    private static String ftpUser=PropertiesUtil.getPropertity("ftp.user");
//    private static String ftpPass=PropertiesUtil.getPropertity("ftp.pass");
    private static String ftpIp = "192.168.43.95";
    private static String ftpUser = "root";
    private static String ftpPass = "321asd";
    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    private boolean connectServer(String ip, int port, String user, String pwd) {
        ftpClient = new FTPClient();
        Boolean isSuccess = false;
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            log.error("连接ftp服务器失败", e);
        }
        return isSuccess;
    }

    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean upload = true;
        FileInputStream fileInputStream = null;
        //connect to ftpServer
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File fileItem : fileList
                ) {
                    fileInputStream = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fileInputStream);
                }
            } catch (IOException e) {
                log.error("上传文件异常", e);
                upload = false;
            } finally {
                fileInputStream.close();
                ftpClient.disconnect();
            }
        }
        return upload;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        log.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile("img", fileList);
        log.info("开始连接ftp服务器，结束上传，上传结果{}");
        return result;
    }
}
