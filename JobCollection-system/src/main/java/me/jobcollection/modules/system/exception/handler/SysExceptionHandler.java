package me.jobcollection.modules.system.exception.handler;

import lombok.extern.slf4j.Slf4j;
import me.jobcollection.modules.system.domain.vo.Result;
import me.jobcollection.modules.system.exception.BadRequestException;
import me.jobcollection.modules.system.exception.FileException;
import me.jobcollection.modules.system.exception.JobSubmitException;
import me.jobcollection.modules.system.service.JobLogService;
import me.jobcollection.modules.common.utils.enums.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一异常处理
 *
 * @author Hongrry
 * @create 2021-10-05 15:27
 */
@Slf4j
@RestControllerAdvice
public class SysExceptionHandler {
    @Autowired
    private JobLogService jobLogService;

    @ExceptionHandler(FileException.class)
    @ResponseBody
    public void doException(FileException e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendError(400, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Result handlerBadCredentials(BadCredentialsException exception) {
        log.error(exception.getMessage());
        return Result.fail(StatusCode.ACCOUNT_PWD_NOT_EXIST.getCode(), StatusCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
    }

    @ExceptionHandler(BadRequestException.class)
    public Result handlerBadRequest(BadRequestException exception) {
        return Result.fail(StatusCode.BAD_REQUEST.getCode(), exception.getMessage());
    }

    @ExceptionHandler(JobSubmitException.class)
    public Result handlerJobSubmitException(JobSubmitException e) {

        // 记录
        jobLogService.addErrorLog(e.getJobId(), e.getMessage());

        return Result.fail(StatusCode.JOB_SUBMIT_FAILED.getCode(), e.getMessage());
    }
}
