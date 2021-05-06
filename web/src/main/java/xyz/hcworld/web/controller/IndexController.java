package xyz.hcworld.web.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import xyz.hcworld.common.lang.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 主页的控制器
 *
 * @ClassName: IndexController
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
@RestController
public class IndexController extends BaseController {
    /**
     * 首页不需要认证
     * @return
     */
    @GetMapping({"", "/", "index"})
    public Result index() {
        List<String> list = new ArrayList<>(16);;
        list.add("测试");
        list.add("zxc");
        System.out.println(currencyService.selectExistence("m_user",new HashMap<String,Object>(){{
            put("email","zhang@hcworld.xyz");
        }}));
        System.out.println(currencyService.selectExistence("m_user",new HashMap<String,Object>(){{
            put("email","123@hcworld.xyz");
            put("username","随机码");
        }}));
        return Result.success(list);
    }

    /**
     *需要认证
     * @return
     */
    @GetMapping("/user/auth")
    @RequiresAuthentication
    public Result requireAuth() {
        return Result.success("123456");
    }

    /**
     * 拥有admin或者user身份的才能访问
     * @return
     */
    @GetMapping("/message")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public Result getMessage() {
        return Result.success("get获取user或admin信息");
    }
    /**
     * 拥有admin或者user身份的才能访问
     * @return
     */
    @PostMapping("/message")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public Result postMessage() {
        return Result.success("post提交的user或admin信息");
    }

    /**
     * 拥有admin或者user身份的才能访问
     * @return
     */
    @PutMapping("/message")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public Result putMessage() {
        return Result.success("put更新的user或admin信息");
    }

    /**
     * 拥有admin或者user身份的才能访问
     * @return
     */
    @DeleteMapping("/message")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public Result delMessage() {
        return Result.success("delete要删除提交的user或admin信息");
    }

    /**
     * 只有admin身份能访问
     * @return
     */
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public Result requireRole() {
        return Result.success("");
    }

    /**
     * 拥有admin或者user身份的才能访问
     * @return
     */
    @GetMapping("/vipMessage")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    @RequiresPermissions("vip")
    public Result getVipMessage() {
        return Result.success("user或admin");
    }



}
