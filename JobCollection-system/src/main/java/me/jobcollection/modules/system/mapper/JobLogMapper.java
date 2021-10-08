package me.jobcollection.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.jobcollection.modules.system.domain.JobLog;
import me.jobcollection.modules.system.service.dto.JobLogDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hongrry
 * @create 2021-10-04 17:15
 */
@Mapper
public interface JobLogMapper extends BaseMapper<JobLog> {
    /**
     * @param page
     * @param userId
     * @param year
     * @param month
     * @param day
     * @param success
     * @param keyword
     * @return
     */
    IPage<JobLogDetail> listJobLogDetail(Page page,
                                         Long userId,
                                         Integer year,
                                         Integer month,
                                         Integer day,
                                         Boolean success,
                                         String keyword);
}
