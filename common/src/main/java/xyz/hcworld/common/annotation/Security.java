package xyz.hcworld.common.annotation;

import java.lang.annotation.*;


/**
 * 自定义加密AOP记录注解
 *
 * @ClassName: Security
 * @Author: 张红尘
 * @Date: 2021-05-06
 * @Version： 1.0
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Security {
    /**
     * 模块 
     */
    String title() default "";

}
