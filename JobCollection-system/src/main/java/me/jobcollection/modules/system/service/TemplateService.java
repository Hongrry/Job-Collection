package me.jobcollection.modules.system.service;

import me.jobcollection.modules.system.domain.Template;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-08 14:19
 */
public interface TemplateService {
    /**
     * 获取所有的 模板
     *
     * @return
     */
    List<Template> listTemplate();
}
