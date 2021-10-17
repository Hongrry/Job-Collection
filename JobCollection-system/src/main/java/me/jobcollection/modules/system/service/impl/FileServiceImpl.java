package me.jobcollection.modules.system.service.impl;

import cn.hutool.core.io.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jobcollection.modules.common.config.FileProperties;
import me.jobcollection.modules.security.service.dto.JwtUserDto;
import me.jobcollection.modules.security.utils.SpringSecurityUtils;
import me.jobcollection.modules.system.domain.Course;
import me.jobcollection.modules.system.exception.BadRequestException;
import me.jobcollection.modules.system.exception.FileException;
import me.jobcollection.modules.system.exception.JobSubmitException;
import me.jobcollection.modules.system.mapper.CourseMapper;
import me.jobcollection.modules.system.service.FileService;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.UserDto;
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
    private final CourseMapper courseMapper;
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

    }

    @Override
    public String handleFile(JobDto jobDto, String url, JwtUserDto currentUser) {
        // 查询命名模板
        Course course = courseMapper.selectById(jobDto.getCourseId());
        String template = course.getTemplate();


        StringBuilder builder = new StringBuilder(template
                .replace("JOBNAME", jobDto.getJobName())
                .replace("NICKNAME", currentUser.getUser().getNickname())
                .replace("USERNAME", currentUser.getUsername()))
                .append(".")
                .append(StringUtils.substringAfterLast(url, "."))
                .insert(0, course.getName() + File.separator + jobDto.getJobName() + File.separator);

        File file = new File(fileProperties.getPath() + url);
        File newFile = new File(fileProperties.getBaseUploadPath() + builder);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileUtil.writeFromStream(fileInputStream, newFile);

        return builder.toString();
    }
}
