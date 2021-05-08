package xyz.hcworld.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义日志AOP记录注解
 *
 * @ClassName: Log
 * @Author: 张红尘
 * @Date: 2021-05-06
 * @Version： 1.0
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块 
     */
    String title() default "";

}
