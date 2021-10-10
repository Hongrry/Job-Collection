package me.jobcollection.modules.system.service;

import me.jobcollection.modules.system.domain.Dept;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-08 14:26
 */
public interface DeptService {
    /**
     * 查询所有的部门
     *
     * @return
     */
    List<Dept> listDept();
}
