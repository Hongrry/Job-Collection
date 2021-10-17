package me.jobcollection.modules.system.service;

import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Hongrry
 * @create 2021-10-05 13:12
 */
public interface FileService {
    /**
     * 写入本地文件
     *
     * @param file
     * @param user
     * @return
     */
    String writeLocalFile(MultipartFile file,UserDto user);

    /**
     * 上传文件
     *
     * @param file
     * @param fileName
     * @param jobId
     * @return
     */
    void upload(File file, String fileName, Long jobId);

    /**
     *
     * @param jobDto
     * @param url
     * @param currentUser
     * @return
     */
    String handleFile(JobDto jobDto, String url, JwtUserDto currentUser);
}
