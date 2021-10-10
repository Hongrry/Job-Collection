package me.jobcollection.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.jobcollection.modules.system.domain.Template;
import me.jobcollection.modules.system.mapper.TemplateMapper;
import me.jobcollection.modules.system.service.TemplateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hongrry
 * @create 2021-10-08 14:20
 */
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateMapper templateMapper;

    @Override
    public List<Template> listTemplate() {
        return templateMapper.selectList(null);
    }
}
