package me.jobcollection.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.service.DeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hongrry
 * @create 2021-10-08 14:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("dept")
public class DeptController {
    private final DeptService service;

    @GetMapping
    public Result listDept() {
        return Result.success(service.listDept());
    }
}
