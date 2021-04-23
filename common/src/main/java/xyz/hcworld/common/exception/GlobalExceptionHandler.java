package xyz.hcworld.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.hcworld.common.lang.Result;


/**
 * 全局异常处理
 * @ClassName: GlobalExceptionHandler
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 统一异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public Result handle(Exception e)  {
        log.error("统一异常处理捕获一条异常：{}",e.getMessage());
        return Result.fail(e.getMessage());
    }


}
