package me.jobcollection;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.jobcollection.modules.system.domain.Job;
import me.jobcollection.modules.system.domain.JobLog;
import me.jobcollection.modules.system.domain.vo.JobLogVo;
import me.jobcollection.modules.system.domain.vo.JobVo;
import me.jobcollection.modules.system.mapper.JobLogMapper;
import me.jobcollection.modules.system.mapper.JobMapper;
import me.jobcollection.modules.system.mapper.UserMapper;
import me.jobcollection.modules.system.service.JobLogService;
import me.jobcollection.modules.system.service.JobService;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobLogDetail;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.geom.QuadCurve2D;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hongrry
 * @create 2021-10-01 12:32
 */
@SpringBootTest
public class MapperTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    JobMapper jobMapper;
    @Autowired
    JobService jobService;
    @Autowired
    JobLogMapper jobLogMapper;

    @Test
    public void testListJob() {
        Page<JobDto> page = new Page<>(1, 10);
        IPage<JobDto> iPage = jobMapper.selectJobDetailById(page,
                1190113189L,
                null,
                2021,
                10,
                false,
                null
        );
        for (JobDto record : iPage.getRecords()) {

            System.out.println(record);
        }
    }

}
