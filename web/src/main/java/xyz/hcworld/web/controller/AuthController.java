package xyz.hcworld.web.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import xyz.hcworld.common.lang.Result;
import xyz.hcworld.model.User;


/**
 * 用户验证控制器
 * @ClassName: AuthController
 * @Author: 张红尘
 * @Date: 2021-04-24
 * @Version： 1.0
 */
@Slf4j
@RestController
public class AuthController extends BaseController {

    /**
     * 注册请求
     *
     * @param user   表单是用户名以及密码邮箱等
     * @param repass 重复密码
     * @return
     */
    @PostMapping("/register")
    public Result doRegister(@Validated User user, String repass) {
        // 两次密码判断
        if (!user.getPassword().equals(repass)) {
            return Result.fail("两次密码不相同");
        }
        // 完成注册
        return userService.register(user);
    }

    @PostMapping("/login")
    public Result login(@RequestParam(value="account",defaultValue="") String account,
                        @RequestParam(value="password",defaultValue="") String password) {
        //账号密码为空
        if (StrUtil.isEmpty(account) || StrUtil.isEmpty(password)) {
            return Result.fail("账号或密码不能为空");
        }
        //完成登录
        return userService.login(account,password);
    }



    @PostMapping(path = {"/unauthorized","/out"})
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public ResponseEntity<Result> unauthorized()  {
        return new ResponseEntity<>(Result.fail("退出成功"), HttpStatus.UNAUTHORIZED);
    }

}
