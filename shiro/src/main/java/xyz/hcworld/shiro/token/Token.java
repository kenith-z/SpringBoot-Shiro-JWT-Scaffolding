package xyz.hcworld.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName: token
 * @Author: 张红尘
 * @Date: 2021-04-25
 * @Version： 1.0
 */
public class Token implements AuthenticationToken {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
