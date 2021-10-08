package me.jobcollection.modules.system.service;

import me.jobcollection.modules.system.service.dto.JobDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Hongrry
 * @create 2021-10-05 13:12
 */
public interface FileService {
    /**
     * 写入本地文件
     *
     * @param file
     * @return
     */
    String writeLocalFile(MultipartFile file);

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
     * 处理文件 分类 命名
     *
     * @param jobDto
     * @param url
     * @return string
     */
    String handleFile(JobDto jobDto, String url);
}
