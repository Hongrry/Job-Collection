package me.jobcollection;

import me.jobcollection.modules.system.service.JobService;
import me.jobcollection.modules.system.service.dto.JobDto;
import me.jobcollection.modules.system.service.dto.JobQueryCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Hongrry
 * @create 2021-10-07 11:39
 */
@SpringBootTest
public class ServiceTest {
    @Autowired
    JobService jobService;

    @Test
    void testListJob() {
        JobQueryCriteria criteria = new JobQueryCriteria();
        criteria.setPage(1);
        criteria.setPageSize(10);
        criteria.setMonth(10);
        criteria.setYear(2021);
        criteria.setSuccess(null);
    }

    @Test
    void publishJob() {
        JobDto jobDto = new JobDto();
        jobDto.setJobName("Test publish");
        jobDto.setDeadline(1633844928000L);
        jobDto.setTemplateId(1L);
        jobDto.setCourseId(1L);
        jobDto.setDeptId(1L);

        jobService.publishJob(jobDto);
    }
}
