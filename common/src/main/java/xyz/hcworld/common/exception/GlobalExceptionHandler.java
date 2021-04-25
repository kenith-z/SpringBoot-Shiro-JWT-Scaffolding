package xyz.hcworld.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * 用于处理参数校验错误
     * @Validated校验失败时会抛出MethodArgumentNotValidException异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result handle(BindException e) {
        log.warn("统一异常处理捕获一条空参数异常：{}","");
        return Result.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 统一异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public Result handle(Exception e)  {
        log.error("统一异常处理捕获一条Exception异常：{}",e.getMessage());
        return Result.fail(e.getMessage());
    }


}
