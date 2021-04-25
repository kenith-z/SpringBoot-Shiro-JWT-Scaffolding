package xyz.hcworld.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
