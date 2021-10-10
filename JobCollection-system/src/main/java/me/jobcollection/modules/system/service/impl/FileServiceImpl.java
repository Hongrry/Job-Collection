package me.jobcollection.modules.system.service.impl;

import cn.hutool.core.io.FileUtil;
import com.upyun.RestManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jobcollection.config.FileProperties;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.utils.SpringSecurityUtils;
import me.jobcollection.modules.system.exception.BadRequestException;
import me.jobcollection.modules.system.exception.FileException;
import me.jobcollection.modules.system.exception.JobSubmitException;
import me.jobcollection.modules.system.mapper.TemplateMapper;
import me.jobcollection.modules.system.service.FileService;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.UserDto;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Hongrry
 * @create 2021-10-05 13:19
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final RestManager restManager;
    private final TemplateMapper templateMapper;
    private final FileProperties fileProperties;

    @Override
    public String writeLocalFile(MultipartFile file) {
        // 检查文件大小
        if (file.getSize() > fileProperties.getMaxSize() * 1024 * 1024) {
            throw new FileException("文件大小超过范围");
        }
        // 检查后缀
        String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        if (!fileProperties.getSupportSuffix().contains(suffix)) {
            throw new FileException("不支持的文件类型");
        }
        String path = UUID.randomUUID() + "." + suffix;
        File newFile = new File(fileProperties.getPath() + path);
        try {
            FileUtil.writeFromStream(file.getInputStream(), newFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("文件上传错误");
        }
        UserDto user = SpringSecurityUtils.getCurrentUser().getUser();

        // 添加到数据库
        log.info(user.getUsername() + user.getNickname() + "提交的文件" + "{}", path);
        return path;
    }

    @Override
    public void upload(File file, String fileName, Long jobId) {
        byte[] bytes = null;
        try {
            bytes = FileUtil.readBytes(file);
        } catch (Exception e) {
            throw new JobSubmitException(jobId, "文件损坏, 请重新上传");
        }

        // 上传
        try {
            Response response = restManager.writeFile(fileProperties.getBaseUploadPath() + fileName, bytes, null);
        } catch (Exception e) {
            throw new BadRequestException("系统异常, 云存储错误");
        }
    }

    @Override
    public String handleFile(JobDto jobDto, String url, JwtUserDto currentUser) {
        // 根据 job id 查询 命名模板
        String template = templateMapper.selectById(jobDto.getTemplateId()).getTemplate();

        // 修改作业名
        template = template
                .replace("JOBNAME", jobDto.getJobName())
                .replace("NICKNAME", currentUser.getUser().getNickname())
                .replace("USERNAME", currentUser.getUsername());

        // 获取后缀
        String suffix = StringUtils.substringAfterLast(url, ".");
        // 构建目录
        String dir = jobDto.getCourseName() + "/" + jobDto.getJobName() + "/";

        String newPath = dir + template + "." + suffix;
        File file = new File(fileProperties.getPath() + url);
        File newFile = new File(fileProperties.getBaseUploadPath() + newPath);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileUtil.writeFromStream(fileInputStream, newFile);

        // upload(file, newPath, jobDto.getJobId());
        return newPath;
    }
}
