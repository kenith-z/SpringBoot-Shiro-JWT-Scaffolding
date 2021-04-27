package xyz.hcworld.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import xyz.hcworld.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具
 *
 * @ClassName: JWTUtil
 * @Author: 张红尘
 * @Date: 2021-04-25
 * @Version： 1.0
 */
public class JwtUtil {

    /**
     * 用户id
     */
    private static final String ID="id";
    /**
     * 用户昵称
     */
    private static final String USER_NAME="username";
    /**
     * 用户状态
     */
    private static final String STATUS="status";

    /**
     * 过期时间5分钟
     */
    private static long EXPIRE_TIME;
    /**
     * 秘钥
     */
    private static String SECRET;

    /**
     * 从配置文件中注入秘钥
     * @param secret 秘钥
     */
    static void setSecret(String secret){
        SECRET = secret;
    }
    /**
     * 从配置文件中注入秘钥
     * @param expireTime 存活时间
     */
    static void setExpireTime(long expireTime){
        EXPIRE_TIME = expireTime;
    }
    /**
     * 校验token是否正确
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token, User user ) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(ID,user.getId())
                    .withClaim(USER_NAME, user.getUsername())
                    .withClaim(STATUS,user.getStatus())
                    .build();
            System.out.println(1);
            System.out.println(verifier.verify(token));
            System.out.println(2);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static User getUserInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            User user = new User();
            user.setId(jwt.getClaim(ID).asLong());
            user.setUsername(jwt.getClaim(USER_NAME).asString());
            user.setStatus(jwt.getClaim(STATUS).asInt());
            return user;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param user 用户信息
     * @return 加密的token
     */
    public static String sign(User user) {
        Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        //加密算法
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        // 附带username信息
        return JWT.create()
                .withClaim(ID,user.getId())
                .withClaim(USER_NAME, user.getUsername())
                .withClaim(STATUS,user.getStatus())
                //结束时间
                .withExpiresAt(date)
                //创建jwt使用指定算法进行加密
                .sign(algorithm);
    }

}
