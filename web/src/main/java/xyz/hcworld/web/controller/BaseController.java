package xyz.hcworld.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import xyz.hcworld.service.CurrencyService;
import xyz.hcworld.service.UserService;
import xyz.hcworld.util.RedisUtil;

/**
 * 公共控制器
 * @ClassName: BaseController
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
public class BaseController {
    /**
     * 通用服务
     */
    @Autowired
    CurrencyService currencyService;
    /**
     * 用户服务
     */
    @Autowired
    UserService userService;

    /**
     * redis服务
     */
    @Autowired
    RedisUtil redisUtil;


}
