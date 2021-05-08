package xyz.hcworld.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import xyz.hcworld.common.constant.SystemConstant;
import xyz.hcworld.common.lang.Result;
import xyz.hcworld.gotool.security.AesRsaMixedUtils;

import xyz.hcworld.gotool.security.entity.DecryptInfo;
import xyz.hcworld.gotool.security.entity.EncryptedInfo;
import xyz.hcworld.util.RedisUtil;


/**
 * 加解密AOP处理
 *
 * @ClassName: LogAspect
 * @Author: 张红尘
 * @Date: 2021-05-06
 * @Version： 1.0
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class SecurityAspect {
    /**
     * 解析JSON类
     */
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 拦截xyz.hcworld.common.annotation.Security注解的方法
     */
    @Pointcut("@annotation(xyz.hcworld.common.annotation.Security)")
    public void security() {
        System.out.println("经过AOP的Security方法");
    }


    /**
     * 前置增强
     */
    @Around("security()")
    public Object doBefore(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            //判断是否是加密类
            if (args[i] instanceof EncryptedInfo) {
                String privateKey = String.valueOf(redisUtil.get(SystemConstant.PRIVATE_KEY));

                DecryptInfo decryptInfo;
                //解密
                decryptInfo = AesRsaMixedUtils.decode((EncryptedInfo) args[i], privateKey);
                //转成接口定义的类
                args[i] = mapper.readValue(decryptInfo.getData(), args[i].getClass());

            }
        }
        return point.proceed(args);

    }


    /**
     * 拦截最后返回
     *
     * @param result 返回结果
     */
    @AfterReturning(returning = "result", pointcut = "security()")
    public void doAfterReturn(Result result) {
        log.info("LogAspect拦截返回:{}", result.toString());
    }


}
