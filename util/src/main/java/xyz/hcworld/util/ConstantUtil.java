package xyz.hcworld.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ConstantUtil
 * @Author: 张红尘
 * @Date: 2021-04-26
 * @Version： 1.0
 */
@Component
public class ConstantUtil {

    @Value("${jwt.secret}")
    private void setSecret(String secret){
        JwtUtil.setSecret(secret);
    }

    @Value("${jwt.expireTime}")
    private void setExpireTime(long expireTime){
        JwtUtil.setExpireTime(expireTime);
    }

}
