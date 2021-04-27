package xyz.hcworld.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.hcworld.model.User;
import xyz.hcworld.shiro.config.Status;
import xyz.hcworld.util.JwtUtil;
import xyz.hcworld.mapper.UserMapper;
import xyz.hcworld.shiro.token.Token;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: AccountRealm
 * @Author: 张红尘
 * @Date: 2021-04-25
 * @Version： 1.0
 */
@Component
public class AccountRealm extends AuthorizingRealm {



    @Autowired
    private Status status;
    @Autowired
    private UserMapper userMapper;


    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Token;
    }

    /**
     * 登录认证
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");

        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        User jwtUser = JwtUtil.getUserInfo(token);

        if (jwtUser == null) {
            throw new AuthenticationException("该用户不存在！");
        }
        if (!JwtUtil.verify(token, jwtUser)) {
            throw new AuthenticationException("token认证失败！");
        }
         else if (jwtUser.getStatus() < 0) {
            throw new AuthenticationException("该用户已被封号！");
        }
        return new SimpleAuthenticationInfo(token, token, "AccountRealm");
    }

    /**
     * 用户授权
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");
        User user = JwtUtil.getUserInfo(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Set<String> roleSet = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();

        // 获得该用户角色
        roleSet.add(status.getStatus(user.getStatus()));
        // 每个角色拥有默认的权限
        permissionSet.add(status.getStatus(user.getStatus() < 0 ? -1 : 0));
        // 每个用户可以设置新的权限
        permissionSet.add(status.getStatus(user.getStatus()));
//        权限补充permissionSet.add("vip");

        // 设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }

}
