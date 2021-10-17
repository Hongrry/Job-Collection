package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.common.rest.BaseController;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Hongrry
 * @create 2021-10-05 13:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("file")
public class FileController extends BaseController {
    private final FileService fileService;

    /**
     * 存到本地
     *
     * @param file
     * @return
     */
    @PostMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String path = fileService.writeLocalFile(file, getCurrentUser());
        return Result.success(path);
    }
}
