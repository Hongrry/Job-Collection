package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.mapper.TemplateMapper;
import me.jobcollection.modules.system.service.TemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hongrry
 * @create 2021-10-08 14:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("template")
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping
    public Result listTemplate() {
        return Result.success(templateService.listTemplate());
    }
}
