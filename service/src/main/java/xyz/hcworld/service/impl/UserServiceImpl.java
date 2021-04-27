package xyz.hcworld.service.impl;


import cn.hutool.crypto.SmUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import xyz.hcworld.common.lang.Result;
import xyz.hcworld.gotool.random.Random;
import xyz.hcworld.mapper.CurrencyMapper;
import xyz.hcworld.mapper.UserMapper;
import xyz.hcworld.model.User;
import xyz.hcworld.service.UserService;
import xyz.hcworld.util.JwtUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 用户服务类实现
 *
 * @ClassName: UserServiceImpl
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 通用数据操作
     */
    @Autowired
    CurrencyMapper currencyMapper;
    /**
     * 用户数据操作
     */
    @Autowired
    UserMapper userMapper;

    @Override
    public Result register(User user) {
        // 条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("email", user.getEmail()).or()
                .eq("username", user.getUsername()).or()
                .eq("mobile", user.getMobile());
        Integer existence = currencyMapper.selectExistence("m_user", wrapper);
        if (existence != null) {
            return Result.fail("用户名或邮箱、电话已被占用");
        }
        //内容审核

        // 安全性设置，防止前端传恶意邮箱，用户名密码以外的属性
        User temp = new User();
        temp.setUsername(HtmlUtils.htmlEscape(user.getUsername()));
        temp.setEmail(user.getEmail());
        temp.setMobile(user.getMobile());
        temp.setIv(Random.lettersAndNum(8));
        // 国密sm3摘要算法
        temp.setPassword(SmUtil.sm3(temp.getIv() + user.getPassword()));
        temp.setAvatar(user.getAvatar() != null ? user.getAvatar() : "");
        temp.setBirthday(LocalDate.now());
        temp.setCreated(LocalDateTime.now());
        temp.setModified(LocalDateTime.now());
        temp.setLasted(LocalDateTime.now());
        return this.save(temp) ? Result.success().action("/login") : Result.fail("请稍后再试");
    }

    @Override
    public Result login(String account, String password) {

        User user=  userMapper.selectUserWhereAccount(account);
        if(user==null){
            return Result.fail("用户不存在");
        }else if (!user.getPassword().equals(SmUtil.sm3(user.getIv() + password))){
            return Result.fail("密码错误");
        }else if (user.getStatus()<0){
            return Result.fail("账号被封禁");
        }
        return Result.success(JwtUtil.sign(user));
    }
}
