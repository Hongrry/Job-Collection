package me.jobcollection.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.Dept;
import me.jobcollection.modules.system.mapper.DeptMapper;
import me.jobcollection.modules.system.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-08 14:26
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {
    private final DeptMapper deptMapper;

    @Override
    public List<Dept> listDept() {
        return deptMapper.selectList(null);
    }
}
